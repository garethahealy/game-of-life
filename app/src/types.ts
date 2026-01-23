export type CellState = 'DEAD' | 'ALIVE'

export interface Cell {
    state: CellState
    xCords: number
    yCords: number
}

export interface SeedOption {
    id: string
    label: string
}
