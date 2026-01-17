import type { SeedOption } from './types'

const rawBaseUrl = import.meta.env.VITE_BACKEND_BASE_URL ?? ''

export const API_BASE_URL = rawBaseUrl.replace(/\/$/, '')
export const POLL_INTERVAL_MS = 1000
export const SEEDS: SeedOption[] = [
    { id: 'AllAliveSeed', label: 'All Alive' },
    { id: 'GliderSeed', label: 'Glider' },
    { id: 'GosperGliderGunSeed', label: 'Gosper Glider Gun' },
    { id: 'HeavyweightSpaceshipSeed', label: 'Heavyweight Spaceship' },
    { id: 'SquareSeed', label: 'Square' },
    { id: 'ThreeLineSeed', label: 'Three Line' },
]
