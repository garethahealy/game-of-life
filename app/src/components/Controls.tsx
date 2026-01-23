import React from 'react'
import type { SeedOption } from '../types'

interface ControlsProps {
  isRunning: boolean
  isLoading: boolean
  selectedSeed: string
  seeds: SeedOption[]
  onToggleRun: () => void
  onClear: () => void
  onRandomize: () => void
  onSeedChange: (seedId: string) => void
  onApplySeed: () => void
}

const Controls = ({
  isRunning,
  isLoading,
  selectedSeed,
  seeds,
  onToggleRun,
  onClear,
  onRandomize,
  onSeedChange,
  onApplySeed,
}: ControlsProps) => (
  <div className="controls">
    <button type="button" onClick={onToggleRun}>
      {isRunning ? 'Pause' : 'Start'}
    </button>
    <button type="button" onClick={onClear} disabled={isLoading}>
      Clear
    </button>
    <button type="button" onClick={onRandomize} disabled={isLoading}>
      Randomize
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
