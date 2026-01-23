import React from 'react'
import type { Cell } from '../types'

const CellSpan = ({
  cell,
  cellIndex,
  canToggle,
  onToggle,
}: {
  cell: Cell
  cellIndex: number
  canToggle: boolean
  onToggle: (cell: Cell) => void
}) => (
  <td
    key={cellIndex}
    className={cell.state === 'ALIVE' ? 'cell alive' : 'cell dead'}
    onClick={() => {
      if (canToggle) {
        onToggle(cell)
      }
    }}
  />
)

const Board = ({
  cells,
  canToggle,
  onToggleCell,
}: {
  cells: Cell[][]
  canToggle: boolean
  onToggleCell: (cell: Cell) => void
}) => (
  <div id="board">
    <table>
      <tbody>
        {cells.map((row, rowIndex) => (
          <tr key={rowIndex}>
            {row.map((cell, cellIndex) => (
              <CellSpan
                key={`${cell.xCords}-${cell.yCords}`}
                cell={cell}
                cellIndex={cellIndex}
                canToggle={canToggle}
                onToggle={onToggleCell}
              />
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  </div>
)

export default Board
