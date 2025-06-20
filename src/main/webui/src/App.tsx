import { useState, useEffect } from 'react'

interface Cells {
    rows: Array<Cell[]>;
}

interface Cell {
    state:  State;
    xCords: number;
    yCords: number;
}

enum State {
    Dead = "DEAD",
    Alive = "ALIVE",
}

async function fetchNextGeneration(): Promise<User[]> {
    const response = await fetch('/board/next-generation');
    const data = await response.json();
    return data;
}

function CellSpan(cellIndex, cell) {
    let state;
    if (cell.state === State.Alive) {
        state = <strong>1</strong>
    } else {
        state = 0;
    }

    return <td key={cellIndex}>{state}</td>
}

function Board() {
    const [cells, setCells] = useState<Cells>([]);

    useEffect(() => {
        const interval = setInterval(() => {
            fetchNextGeneration().then((data) => setCells(data));
        }, 2000);

        return () => clearInterval(interval);
 }, []);

    return (
        <div id="board">
            <table>
                <tbody>
                    {cells.rows?.map((row, rowIndex) => (
                        <tr key={rowIndex}>
                            {row.map((cell, cellIndex) => CellSpan(cellIndex, cell))}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

function App() {
    return (
        <div id="app">
            <h1>GoF</h1>
            <Board />
        </div>
    );
}

export default App