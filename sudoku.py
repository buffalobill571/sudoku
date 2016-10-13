# In the input.txt placed the hardest sudoku

import copy
import time


def File(path):
    a = open(path).readlines()
    for i in range(9):
        a[i] = list(map(int, a[i][:len(a[i])-1].split(" ")))
    return a


def findValues(row, column, matrice):
    values = {v for v in range(1, 10)}
    values -= RowValues(row, matrice)
    values -= ColumnValues(column, matrice)
    values -= BlockValues(row, column, matrice)
    return values


def RowValues(row, matrice):
    return set(matrice[row][:])


def ColumnValues(column, matrice):
    return {matrice[r][column] for r in range(9)}


def BlockValues(row, column, matrice):
    rowStart = 3 * (row // 3)
    columnStart = 3 * (column // 3)
    return {
        matrice[rowStart + r][columnStart + c]
        for r in range(3)
        for c in range(3)
    }


def solver(solution):
    while True:
        minPossible = None
        for i in range(9):
            for j in range(9):
                if solution[i][j] != 0:
                    continue
                possible = findValues(i, j, solution)
                possibleCount = len(possible)
                if possibleCount == 0:
                    return False
                if possibleCount == 1:
                    solution[i][j] = possible.pop()
                if not minPossible or possibleCount < len(minPossible[1]):
                    minPossible = ((i, j), possible)
        if not minPossible:
            return True
        elif 1 < len(minPossible[1]):
            break
    r, c = minPossible[0]
    for v in minPossible[1]:
        solutionCopy = copy.deepcopy(solution)
        solutionCopy[r][c] = v
        if solver(solutionCopy):
            for r in range(9):
                for c in range(9):
                    solution[r][c] = solutionCopy[r][c]
            return True
    return False


def solve(original):
    solution = copy.deepcopy(original)
    if solver(solution):                   # returns true or false
        return solution                    # but modifiers solution 2D matrice
    return None


original = File("input.txt")               # Opens file and makes 2D matrice
for i in original:
    print(i)

print()
start = time.time()

solution = solve(original)
end = time.time()
if solution:
    for i in solution:
        print(i)

print()

print("Running time of programm is %.3f second" % (end - start))
