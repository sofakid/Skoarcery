
test_grammar: Skoarcery\terminals.py Skoarcery\nonterminals.py Skoarcery\emissions.py Skoarcery\underskoar.py
	python -m unittest .\Skoarcery\laboaratory\TestDragonSpells.py
	python -m unittest .\Skoarcery\laboaratory\TestTerminals.py
	python -m unittest .\Skoarcery\laboaratory\TestNonTerminals.py
	python -m unittest .\Skoarcery\laboaratory\TestParseTable.py

sc_lexer: test_grammar
	python -m unittest .\Skoarcery\factoary\Code_Lexer_Sc.py

sc_parser: sc_lexer
	python -m unittest .\Skoarcery\factoary\Code_Parser_Sc.py

sc_sanity: 
	python -m unittest .\Skoarcery\laboaratory\Test_Sclang.py

sc_clean:
	rm SuperCollider\Skoar\lex.sc
	rm SuperCollider\Skoar\rdpp.sc

clean: sc_clean

all: sc_parser sc_sanity
