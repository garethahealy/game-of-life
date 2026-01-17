import React from 'react'
import type { Cell, Cells } from '../types'

const CellSpan = ({ cell, cellIndex }: { cell: Cell; cellIndex: number }) => (
    <td key={cellIndex} className={cell.state === 'ALIVE' ? 'cell alive' : 'cell dead'} />
)

const Board = ({ cells }: { cells: Cells }) => (
    <div id="board">
        <table>
            <tbody>
                {cells.rows.map((row, rowIndex) => (
                    <tr key={rowIndex}>
                        {row.map((cell, cellIndex) => (
                            <CellSpan key={`${cell.xCords}-${cell.yCords}`} cell={cell} cellIndex={cellIndex} />
                        ))}
                    </tr>
                ))}
            </tbody>
        </table>
    </div>
)

export default Board
