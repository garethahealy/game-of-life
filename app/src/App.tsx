import React, { useEffect, useState } from 'react'
import { fetchNextGeneration, fetchSeeds, resetBoard, seedBoard } from './api'
import { POLL_INTERVAL_MS } from './config'
import Board from './components/Board'
import Controls from './components/Controls'
import type { Cell, SeedOption } from './types'

function App() {
    const [cells, setCells] = useState<Cell[][] | null>(null)
    const [error, setError] = useState<string | null>(null)
    const [isRunning, setIsRunning] = useState(true)
    const [isLoading, setIsLoading] = useState(false)
    const [seeds, setSeeds] = useState<SeedOption[]>([])
    const [selectedSeed, setSelectedSeed] = useState('')

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
        if (!seed) {
            return
        }

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

    const formatSeedLabel = (seedId: string) =>
        seedId
            .replace(/Seed$/, '')
            .replace(/([a-z])([A-Z])/g, '$1 $2')
            .replace(/([A-Z])([A-Z][a-z])/g, '$1 $2')
            .replace(/\bR Pentomino\b/, 'R-pentomino')

    const loadSeedOptions = async () => {
        setError(null)
        try {
            const seedIds = await fetchSeeds()
            const options = seedIds.map((id) => ({
                id,
                label: formatSeedLabel(id),
            }))
            setSeeds(options)
            if (!selectedSeed && options.length > 0) {
                setSelectedSeed(options[0].id)
            }
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to load seeds')
        }
    }

    useEffect(() => {
        loadInitial()
        void loadSeedOptions()
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
                    seeds={seeds}
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
