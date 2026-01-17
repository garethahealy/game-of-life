import { API_BASE_URL } from './config'
import type { Cells } from './types'

const fetchCells = async (endpoint: string, options?: RequestInit): Promise<Cells> => {
    const response = await fetch(endpoint, options)
    if (!response.ok) {
        throw new Error(`Request failed: ${response.status}`)
    }

    return response.json() as Promise<Cells>
}

export const fetchNextGeneration = () => fetchCells(`${API_BASE_URL}/board/next-generation`)
export const resetBoard = () => fetchCells(`${API_BASE_URL}/board/reset`)
export const seedBoard = (seed: string) => fetchCells(`${API_BASE_URL}/board/seed/${encodeURIComponent(seed)}`, { method: 'POST' })
