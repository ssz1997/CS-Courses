#This is project 4 of Fall 2017 CSCI 312
#Created by Chen, Yining and Sun, Shouzhuo



import re, sys, string

debug = False
state = { }
tokens = [ ]
tm = { }



class StatementList( object ):
	def __init__(self):
		self.statementList = []

	def addStatement(self, statement):
		self.statementList.append(statement)

	def __str__(self):
		printStr = ''
		for statement in self.statementList:
			printStr += str(statement)
		return printStr
	
	def meaning(self,state):
                for statement in self.statementList:
                        statement.meaning(state)
                                
                return state
	def tipe(self, tm):
		for statement in self.statementList:
			statement.tipe(tm)
		return tm


# === Statement class and subclasses
class Statement( object ):
	def __str__(self):
		return ""

class Assignment( Statement ):
	def __init__(self, identifier, expression):
		self.identifier = identifier
		self.expression = expression
		
	def __str__(self):
		return "= " + str(self.identifier) + " " + str(self.expression)+ "\n"

	def meaning(self,state):
                state[self.identifier] = self.expression.value(state)
                return state
	
	
	def tipe(self, tm):
		tp = self.expression.tipe(tm);
		if (tp == ""):
			for key in tm:
				print (str(key) + " " + str(tm[key]))			
			error ("Type Error: " + self.identifier + " is referenced before being defined!")
		if self.identifier not in tm :
			tm[self.identifier] = tp
			return tm
		else:
			if (tm[self.identifier] != tp):
				for key in tm:
					print (str(key) + " " + str(tm[key]))				
				error ("Type mismatch: " + str(tm[self.identifier]) + " = " + tp + "!")	


class WhileStatement( Statement ):
	def __init__(self, expression, block):
		self.expression = expression
		self.block = block

	def __str__(self):
		return "while " + str(self.expression) + "\n" + str(self.block) + "endwhile\n"

	def meaning(self,state):
                while self.expression.value(state) == 1:
                        self.block.meaning(state)
                return 
	def tipe(self, tm):
		
		self.expression.tipe(tm)
		self.block.tipe(tm)
                        

class IfStatement( Statement ):
	def __init__(self, expression, ifBlock, elseBlock):
		self.expression = expression
		self.ifBlock = ifBlock
		self.elseBlock = elseBlock

	def __str__(self):
		
		return "if " + str(self.expression) + "\n" + str(self.ifBlock) + "else\n" + str(self.elseBlock) + "endif\n"

	def meaning(self,state):
                if self.expression.value(state) == 1:
                        self.ifBlock.meaning(state)
                else:
                        if self.elseBlock != '':
                                self.elseBlock.meaning(state)
                return state
	
	def tipe(self, tm):
		
		self.expression.tipe(tm)
		self.ifBlock.tipe(tm)
		if self.elseBlock != '':
			self.elseBlock.tipe(state)		
		



# === Expression class and subclasses
class Expression( object ):
	def __str__(self):
		return "" 
	
class BinaryExpr( Expression ):
	def __init__(self, op, left, right):
		self.op = op
		self.left = left
		self.right = right
		
	def __str__(self):
		return str(self.op) + " " + str(self.left) + " " + str(self.right)

	def value(self,state):
                left = self.left.value(state)
                right = self.right.value(state)
                if self.op == "+":
                        return left + right
                if self.op == "-":
                        return left-right
                if self.op == "*":
                        return left * right
                if self.op == "/":
                        return left / right
                if self.op == ">":
                        return int(left > right)
                if self.op == "<":
                        return int(left < right)
                if self.op == "==":
                        return int(left == right)
                if self.op == ">=":
                        return int(left >= right)
                if self.op == "<=":
                        return int(left <= right)
                if self.op == "!=":
                        return int(left != right)
                if self.op == "and":
                        if left:
                                return int(right)
                        else:
                                return 0
                if self.op == "or":
                        if left:
                                return 1
                        else:
                                return int(right)
	def tipe( self, tm):
		left = self.left.tipe(tm)
		right = self.right.tipe(tm)
		if self.op == "+" or self.op == "-" or self.op == "*" or self.op == "/":
			if left != "number" or right != "number":
				for key in tm:
					print (str(key) + " " + str(tm[key]))				
				error("Type Mismatch:" + left + self.op + right + "!")
			return "number"
		if self.op == "==" or self.op == ">" or self.op == "<" or self.op == "<=" or self.op == ">=" or self.op == "!=":
			if left != "number" or right != "number":
				for key in tm:
					print (str(key) + " " + str(tm[key]))				
				error("Type Mismatch:" + left + self.op + right+ "!")
			return "boolean"
		if self.op == "and" or "or":
			if left != "boolean" or right != "boolean":
				for key in tm:
					print (str(key) + " " + str(tm[key]))				
				error("Type Mismatch:" + left + self.op + right+ "!")
			return "boolean"
                           

class Number( Expression ):
	def __init__(self, value):
		self.__value = value
		
	def __str__(self):
		return str(self.__value)

	def value(self, state):
                return int(self.__value)
	
	def tipe(self, tm):
		return "number"


class VarRef( Expression ):
	def __init__(self, value):
		self.__value = value

	def __str__(self):
		return str(self.__value)
	
	def value(self, state):
                return int(state[self.__value])
	
	def tipe(self,tm):
		if self.__value not in tm:
			for key in tm:
				print (str(key) + " " + str(tm[key]))			
			error ("Type Error: " + self.__value + " is referenced before being defined!")
			
		return tm[self.__value]


class String( Expression ):
	def __init__(self, value):
		self.__value = value

	def __str__(self):
		return str(self.__value)
	
	def tipe(self, tm):
		return "string"


# Lexer, a private class that represents lists of tokens from a Gee
# statement. This class provides the following to its clients:
#
#   o A constructor that takes a string representing a statement
#       as its only parameter, and that initializes a sequence with
#       the tokens from that string.
#
#   o peek, a parameterless message that returns the next token
#       from a token sequence. This returns the token as a string.
#       If there are no more tokens in the sequence, this message
#       returns None.
#
#   o removeToken, a parameterless message that removes the next
#       token from a token sequence.
#
#   o __str__, a parameterless message that returns a string representation
#       of a token sequence, so that token sequences can print nicely

class Lexer :
	
	
	# The constructor with some regular expressions that define Gee's lexical rules.
	# The constructor uses these expressions to split the input expression into
	# a list of substrings that match Gee tokens, and saves that list to be
	# doled out in response to future "peek" messages. The position in the
	# list at which to dole next is also saved for "nextToken" to use.
	
	special = r"\(|\)|\[|\]|,|:|;|@|~|;|\$"
	relational = "<=?|>=?|==?|!="
	arithmetic = "\+|\-|\*|/"
	#char = r"'."
	string = r"'[^']*'" + "|" + r'"[^"]*"'
	number = r"\-?\d+(?:\.\d+)?"
	literal = string + "|" + number
	#idStart = r"a-zA-Z"
	#idChar = idStart + r"0-9"
	#identifier = "[" + idStart + "][" + idChar + "]*"
	identifier = "[a-zA-Z]\w*"
	lexRules = literal + "|" + special + "|" + relational + "|" + arithmetic + "|" + identifier
	
	def __init__( self, text ) :
		self.tokens = re.findall( Lexer.lexRules, text )
		self.position = 0
		self.indent = [ 0 ]
	
	
	# The peek method. This just returns the token at the current position in the
	# list, or None if the current position is past the end of the list.
	
	def peek( self ) :
		if self.position < len(self.tokens) :
			return self.tokens[ self.position ]
		else :
			return None
	
	
	# The removeToken method. All this has to do is increment the token sequence's
	# position counter.
	
	def next( self ) :
		self.position = self.position + 1
		return self.peek( )
	
	
	# An "__str__" method, so that token sequences print in a useful form.
	
	def __str__( self ) :
		return "<Lexer at " + str(self.position) + " in " + str(self.tokens) + ">"



# The "parse" function. This builds a list of tokens from the input string,
# and then hands it to a recursive descent parser for the PAL grammar.
# This is where the work is started after the tokens are loaded in correctly.

def parse( text ) :
	global tokens
	tokens = Lexer( text )
	stmtlist = parseStmtList( )
	print (stmtlist)
	return

def meaning( text, state ) :
        global tokens
        tokens = Lexer(text)
        stmtlist = parseStmtList()
        state = stmtlist.meaning(state)
        list1 = []
        for key in state:
                list1.append("<" + str(key) + ", " + str(state[key]) + ">")
        to_print = ", ".join(list1)
        to_print = "{" + to_print + "}"
        print (to_print)
        return

def tipe( text, tm ):
	global tokens
	tokens = Lexer(text)
	stmtlist = parseStmtList()
	tm = stmtlist.tipe(tm)
	for key in tm:
		print (str(key) + " " + str(tm[key]))
	return
        
def parseStmtList(  ):
	""" stmtList =  {  statement  } """

	tok = tokens.peek( )
	statementList = StatementList()

	while tok not in [None ,"~"]: #have to account for undent that signals an end of block

		statement = parseStatement()
		statementList.addStatement(statement)
		tok = tokens.peek()

	return statementList


def parseStatement():
	"""statement = ifStatement |  whileStatement  |  assignment"""

	tok = tokens.peek()
	if debug: print ("Statement: ", tok)

	if tok == "if":
		return parseIfStmt()

	elif tok == "while":
		return parseWhileStmt()

	elif re.match(Lexer.identifier, tok):
		return parseAssignment()

	error("error msg: statement")
	return


def parseAssignment():
	"""assign = ident "=" expression  eoln"""

	identifier = tokens.peek()
	if debug: print ("Assign statement: ", left)

	if tokens.next() != "=":
		error("error msg: assignment")

	tokens.next()
	exp = expression()

	tok = tokens.peek()
	if tok != ";":
		error("error msg: assignment")

	tokens.next()

	return Assignment(identifier, exp)
	


def parseIfStmt():
	"""ifStatement = "if" expression block   [ "else" block ] """

	tok = tokens.next()
	if debug: print ("If statement: ", tok)

	expr = expression()
	ifBlock = parseBlock()
	elseBlock = ''

	if tokens.peek() == "else":
		tok = tokens.next()
		if debug: print ("Else statement: ", tok)
		elseBlock = parseBlock()

	return IfStatement(expr, ifBlock, elseBlock)


def parseWhileStmt():
	"""whileStatement = "while"  expression  block"""

	tok = tokens.next()
	if debug: print ("While statement: ", tok)

	expr = expression()
	block = parseBlock()

	return WhileStatement(expr, block)


def parseBlock():
	"""block = ":" eoln indent stmtList undent"""

	tok = tokens.peek()
	if debug: print ("Block: ", tok)
	
	# Check to make sure each of the appropriate terminal tokens exist and are in the right place
	if tok != ":":
		error("error msg: block")

	tok = tokens.next()

	if tok != ";":
		error("error msg: block")

	tok = tokens.next()

	if tok != "@":
		error("error msg: block")

	tok = tokens.next()

	stmtList = parseStmtList()

	if tokens.peek() != "~":
		error("error msg: block")

	tokens.next()

	return stmtList


def expression():
	"""expression = andExpr { "or" andExpr }"""
	tok = tokens.peek( )
	if debug: print ("expression: ", tok)
	left = andExpr( )
	tok = tokens.peek( )
	while tok == "or":
		tokens.next()
		right = andExpr( )
		left = BinaryExpr(tok, left, right)
		tok = tokens.peek( )
	return left


def andExpr():
	"""andExpr    = relationalExpr { "and" relationalExpr }"""

	tok = tokens.peek( )
	if debug: print ("andExpr: ", tok)
	left = relationalExpr( )
	tok = tokens.peek( )
	while tok == "and":
		tokens.next()
		right = relationalExpr( )
		left = BinaryExpr(tok, left, right)
		tok = tokens.peek( )
	return left


def relationalExpr():
	"""relationalExpr = addExpr [ relation addExpr ]"""

	tok = tokens.peek( )
	if debug: print ("relationalExpr: ", tok)
	left = addExpr( )
	tok = tokens.peek( )
	while re.match(Lexer.relational, tok):
		tokens.next()
		right = addExpr( )
		left = BinaryExpr(tok, left, right)
		tok = tokens.peek( )
	return left


def addExpr( ):
	""" addExpr    = term { ('+' | '-') term } """

	tok = tokens.peek( )
	if debug: print ("addExpr: ", tok)
	left = term( )
	tok = tokens.peek( )
	while tok == "+" or tok == "-":
		tokens.next()
		right = term( )
		left = BinaryExpr(tok, left, right)
		tok = tokens.peek( )
	return left



def term( ):
	""" term    = factor { ('*' | '/') factor } """

	tok = tokens.peek( )
	if debug: print ("Term: ", tok)
	left = factor( )
	tok = tokens.peek( )
	while tok == "*" or tok == "/":
		tokens.next()
		right = factor( )
		left = BinaryExpr(tok, left, right)
		tok = tokens.peek( )
	return left


def factor( ):
	"""factor  = number | string | ident |  "(" expression ")" """

	tok = tokens.peek( )

	if debug: print ("Factor: ", tok)

	if re.match(Lexer.number, tok):
		expr = Number(tok)
		tokens.next( )
		return expr

	elif re.match(Lexer.string, tok):
		expr = String(tok)
		tokens.next()
		return expr

	elif re.match(Lexer.identifier, tok):
		expr = VarRef(tok)
		tokens.next()
		return expr

	if tok == "(":
		tokens.next( )  # or match( tok )
		expr = expression( )
		tokens.peek( )
		tok = match(")")
		return expr

	error("error msg: factor()")
	return


def match(matchtok):
	tok = tokens.peek( )
	if (tok != matchtok): error("error msg: match()")
	tokens.next( )
	return tok

def error( msg ):
	sys.exit(msg)



def mklines(filename):

	inn = open(filename, "r")
	lines = [ ]
	pos = [0]
	ct = 0
	for line in inn:
		ct += 1
		line = line.rstrip( )+";"
		line = delComment(line)
		if len(line) == 0 or line == ";": continue
		indent = chkIndent(line)
		line = line.lstrip( )
		if indent > pos[-1]:
			pos.append(indent)
			line = '@' + line
		elif indent < pos[-1]:
			while indent < pos[-1]:
				del(pos[-1])
				line = '~' + line
		#print (ct, "\t", line)
		lines.append(line)
	# print len(pos)
	undent = ""
	for i in pos[1:]:
		undent += "~"
	lines.append(undent)

	return lines


def chkIndent(line):
	ct = 0
	for ch in line:
		if ch != " ": return ct
		ct += 1
	return ct
		

def delComment(line):
	pos = line.find("#")
	if pos > -1:
		line = line[0:pos]
		line = line.rstrip()
	return line



def main():

	global debug
	ct = 0
	for opt in sys.argv[1:]:
		if opt[0] != "-": break
		ct = ct + 1
		if opt == "-d": debug = True
	if len(sys.argv) < 2+ct:
		print ("Usage:  %s filename" % sys.argv[0])
		return
	#parse("".join(mklines(sys.argv[1+ct])))
	#for line in open(sys.argv[1+ct], "r"):
         #       print (line, end = '')
	tipe("".join(mklines(sys.argv[1+ct])),state)
	return


main()
