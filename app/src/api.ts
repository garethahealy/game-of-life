import { API_BASE_URL } from './config'
import type { Cell } from './types'

const fetchCells = async (endpoint: string, options?: RequestInit): Promise<Cell[][]> => {
    const response = await fetch(endpoint, options)
    if (!response.ok) {
        throw new Error(`Request failed: ${response.status}`)
    }

    return response.json() as Promise<Cell[][]>
}

export const fetchNextGeneration = () => fetchCells(`${API_BASE_URL}/board/next-generation`, { method: 'POST' })
export const resetBoard = () => fetchCells(`${API_BASE_URL}/board/reset`, { method: 'POST' })
export const seedBoard = (seed: string) => fetchCells(`${API_BASE_URL}/seeds/${encodeURIComponent(seed)}`, { method: 'POST' })
export const fetchSeeds = async (): Promise<string[]> => {
    const response = await fetch(`${API_BASE_URL}/seeds`)
    if (!response.ok) {
        throw new Error(`Request failed: ${response.status}`)
    }

    return response.json() as Promise<string[]>
}
