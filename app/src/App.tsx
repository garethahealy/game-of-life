import React, { useEffect, useState } from 'react'
import { fetchNextGeneration, resetBoard, seedBoard } from './api'
import { POLL_INTERVAL_MS, SEEDS } from './config'
import Board from './components/Board'
import Controls from './components/Controls'
import type { Cells } from './types'

function App() {
    const [cells, setCells] = useState<Cells | null>(null)
    const [error, setError] = useState<string | null>(null)
    const [isRunning, setIsRunning] = useState(true)
    const [isLoading, setIsLoading] = useState(false)
    const [selectedSeed, setSelectedSeed] = useState(SEEDS[0].id)

    const loadInitial = async () => {
        setIsLoading(true)
        setError(null)
        try {
            const data = await resetBoard()
            setCells(data)
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to load board')
        } finally {
            setIsLoading(false)
        }
    }

    const handleStep = async () => {
        setIsLoading(true)
        setError(null)
        try {
            const data = await fetchNextGeneration()
            setCells(data)
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to advance')
        } finally {
            setIsLoading(false)
        }
    }

    const handleSeed = async (seed: string) => {
        setIsLoading(true)
        setError(null)
        try {
            const data = await seedBoard(seed)
            setCells(data)
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to seed board')
        } finally {
            setIsLoading(false)
        }
    }

    useEffect(() => {
        loadInitial()
    }, [])

    useEffect(() => {
        if (!isRunning) {
            return
        }

        const interval = setInterval(() => {
            fetchNextGeneration()
                .then((data) => setCells(data))
                .catch((err) => {
                    setError(err instanceof Error ? err.message : 'Failed to advance')
                })
        }, POLL_INTERVAL_MS)

        return () => clearInterval(interval)
    }, [isRunning])

    return (
        <div id="app">
            <header>
                <h1>Game of Life</h1>
                <Controls
                    isRunning={isRunning}
                    isLoading={isLoading}
                    selectedSeed={selectedSeed}
                    seeds={SEEDS}
                    onToggleRun={() => setIsRunning((prev) => !prev)}
                    onStep={handleStep}
                    onReset={loadInitial}
                    onSeedChange={setSelectedSeed}
                    onApplySeed={() => handleSeed(selectedSeed)}
                />
            </header>
            {error && <p className="error">{error}</p>}
            {cells ? <Board cells={cells} /> : <p className="loading">Loading board...</p>}
        </div>
    )
}

export default App
