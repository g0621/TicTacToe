import random
import copy
def prboard(board):
    print(' ' + board[7] + ' | ' + board[8] + ' | ' + board[9] + ' ' + '\n' + '---|---|---')
    print(' ' + board[4] + ' | ' + board[5] + ' | ' + board[6] + ' ' + '\n' + '---|---|---')
    print(' ' + board[1] + ' | ' + board[2] + ' | ' + board[3] + ' ')

def playerletter():
    letter = ''
    while not(letter == 'X' or letter == 'O'):
        letter = input('what do u want "X" or "O" ? ').upper()
    if letter == 'X':
        return ['X','O']
    else:
        return ['O','X']

def whofirst():
    if random.randint(0,1) == 0:
        return 'computer'
    else:
        return 'player'

def playagain():
    return input('do u want to play again ?(yes or no)').lower().startswith('y')

def makemv(board,move,letter):
    board[move] = letter

def isWinner(bo, le):
    return ((bo[7] == le and bo[8] == le and bo[9] == le) or
    (bo[4] == le and bo[5] == le and bo[6] == le) or
    (bo[1] == le and bo[2] == le and bo[3] == le) or
    (bo[7] == le and bo[4] == le and bo[1] == le) or
    (bo[8] == le and bo[5] == le and bo[2] == le) or
    (bo[9] == le and bo[6] == le and bo[3] == le) or
    (bo[7] == le and bo[5] == le and bo[3] == le) or
    (bo[9] == le and bo[5] == le and bo[1] == le))

def isfree(board,move):
    return board[move] == ''

def getplayermv(board):
    avli = [int(x) for x in '123456789']
    move = ''
    while move not in avli or not isfree(board,move):
        move = int(input('whats the next move(1-9) : '))
    return move

def chooserand(board,mvlist):
    posmv = []
    for i in mvlist:
        if isfree(board,i):
            posmv.append(i)
    if len(posmv) != 0:
        return random.choice(posmv)
    else:
        return None

def getCompMovw(board,cletter):
    if cletter == 'X':
        pletter = 'O'
    else:
        pletter = 'X'
    for i in range(1,10):             #checking if comp can win in next move
        cp = copy.deepcopy(board)
        if isfree(cp,i):
            makemv(cp,i,cletter)
            if isWinner(cp,cletter):
                return i

    for i in range(1, 10):              # checking if player can win in next move
        cp = copy.deepcopy(board)
        if isfree(cp, i):
            makemv(cp, i, pletter)
            if isWinner(cp, pletter):
                return i

    if isfree(board,5):
        return 5
    if ((board[5] == cletter) and (board[1] == '') and board[3] == '' and board[7] == '' and board[9] == ''):
        mv = chooserand(board, [1, 3, 7, 9])
        if mv != None:
            return mv

        return chooserand(board, [2, 4, 6, 8])

    if board[5] == cletter:
        mv = chooserand(board, [2,4,6,8])
        if mv != None:
            return mv

        return chooserand(board, [1,3,7,9])
    else:
        mv = chooserand(board, [1, 3, 7, 9])
        if mv != None:
            return mv

        return chooserand(board, [2, 4, 6, 8])



def isfull(board):
    for i in range(1,10):
        if isfree(board,i):
            return False
    return True

print('welcome to tic tac toe')
while True:
    board = ['']*10
    pletter,cletter = playerletter()
    turn = whofirst()
    print('The ' + turn + ' goes first')
    running = True
    while running:
        if turn == 'player':
            mv = getplayermv(board)
            makemv(board,mv,pletter)
            prboard(board)
            if isWinner(board,pletter):
                prboard(board)
                print('u beat the comp.')
                running = False
            else:
                if isfull(board):
                    prboard(board)
                    print('the game is a tie !')
                    break
                else:
                    turn = 'computer'

        else:
            mv = getCompMovw(board,cletter)
            makemv(board,mv,cletter)
            print('computers turn : ')
            prboard(board)
            if isWinner(board,cletter):
                prboard(board)
                print('u lost the game')
                running = False
            else:
                if isfull(board):
                    prboard(board)
                    print('the game is a tie !')
                    break
                else:
                    turn = 'player'
    if not playagain():
        break