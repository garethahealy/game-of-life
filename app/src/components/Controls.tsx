import React from 'react'
import type { SeedOption } from '../types'

interface ControlsProps {
    isRunning: boolean
    isLoading: boolean
    selectedSeed: string
    seeds: SeedOption[]
    onToggleRun: () => void
    onStep: () => void
    onReset: () => void
    onSeedChange: (seedId: string) => void
    onApplySeed: () => void
}

const Controls = ({
    isRunning,
    isLoading,
    selectedSeed,
    seeds,
    onToggleRun,
    onStep,
    onReset,
    onSeedChange,
    onApplySeed,
}: ControlsProps) => (
    <div className="controls">
        <button type="button" onClick={onToggleRun}>
            {isRunning ? 'Pause' : 'Start'}
        </button>
        <button type="button" onClick={onStep} disabled={isLoading}>
            Next
        </button>
        <button type="button" onClick={onReset} disabled={isLoading}>
            Reset
        </button>
        <select value={selectedSeed} onChange={(event) => onSeedChange(event.target.value)} disabled={isLoading}>
            {seeds.map((seed) => (
                <option key={seed.id} value={seed.id}>
                    {seed.label}
                </option>
            ))}
        </select>
        <button type="button" onClick={onApplySeed} disabled={isLoading}>
            Apply Seed
        </button>
    </div>
)

export default Controls
