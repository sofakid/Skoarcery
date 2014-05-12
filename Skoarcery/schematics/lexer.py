from Skoarcery import terminals, emissions

_ = _____ = _________ = _____________ = _________________ = _____________________ = None

# -------
# Symbols
# -------
SkoarToke_ = "SkoarToke"
lexeme_ = "lexeme"
regex_ = "regex"
inspectable_ = "inspectable"
burn_ = "burn"
match_ = "match"
buf_ = "buf"
offs_ = "offs"
toke_class_ = "toke_class"
match_toke_ = "match_toke"
s_ = "s"
SkoarError_ = "SkoarError"
SubclassResponsibilityError_ = "SubclassResponsibilityError"


#
# configure schematics to output a language
# implemented by one of the tongues in emissions
def init(tongue):
    global _, _____, _________, _____________, _________________, _____________________
    assert tongue in emissions.tongues
    _ = _____ = _________ = _____________ = _________________ = _____________________ = tongue


def skoarToke():

    _.cmt_hdr("Abstract Token")

    _.abstract_class(SkoarToke_)

    _____.attrvar("<", lexeme_)
    _____.classvar("<", regex_, _.null)
    _____.classvar("<", inspectable_, _.false)
    _____.nl()

    _____.constructor(s_)
    _________.stmt(_.v_ass(_.v_attr(lexeme_), s_))
    _____.end()

    _____.cmt("how many characters to burn from the buffer")
    _____.method(burn_)
    _________.return_(_.v_length(_.v_attr(lexeme_)))
    _____.end()

    _____.cmt("override and return " + _.null + " for no match, new toke otherwise")
    _____.abstract_static_method(match_, buf_, offs_)
    _________.throw(SubclassResponsibilityError_, _.v_str("What are you doing human?"))
    _____.end()

    _____.cmt("match requested toke")
    _____.static_method(match_toke_, buf_, offs_, toke_class_)
    _________.find_regex(match_, toke_class_ + "." + regex_, buf_, offs_)

    _________.if_(_.v_match(match_))
    _____________.return_(_.v_new(toke_class_, _.v_regex_group_zero(match_)))
    _________.end_if()

    _________.return_(_.null)
    _____.end()

    _.end()


def whitespace_token():

    Whitespace = terminals.Whitespace

    _.cmt_hdr("Whitespace is special")
    _.class_(Whitespace.toker_name, SkoarToke_)
    _____.static_method(burn_, buf_, offs_)
    _________.find_regex(match_, _.v_def_regex(Whitespace.regex), buf_, offs_)
    _________.if_(_.v_match(match_))
    _____________.return_(_.v_length(_.v_regex_group_zero(match_)))
    _________.end_if()
    _________.return_("0")
    _____.end()
    _.end()


def EOF_token():

    EOF = terminals.EOF

    _.cmt_hdr("EOF is special")
    _.class_(EOF.toker_name, SkoarToke_)
    _____.static_method(burn_, buf_, offs_)
    _________.if_(_.v_length(buf_) + " > " + offs_)
    _____________.throw(SkoarError_, _.v_str("Tried to burn EOF when there's more input."))
    _________.end_if()
    _________.return_("0")
    _____.end()
    _____.static_method(match_, buf_, offs_)
    _________.if_(_.v_length(buf_) + " < " + offs_)
    _____________.throw(SkoarError_, _.v_str("Tried to burn EOF when there's more input."))
    _________.end_if()
    _________.if_(_.v_length(buf_) + " == " + offs_)
    _____________.return_(_.v_new(EOF.toker_name, ""))
    _________.end_if()
    _________.return_(_.null)
    _____.end()
    _.end()


def typical_token(token):

    inspectable = _.true if token.name in terminals.inspectables else _.false

    _.class_(token.toker_name, SkoarToke_)
    _____.classvar("<", regex_, _.v_def_regex(token.regex))
    _____.classvar("<", "inspectable", inspectable)
    _____.nl()
    _____.static_method(match_, buf_, offs_)
    _________.return_(SkoarToke_ + "." + match_toke_ + "(" + buf_ + ", " + offs_ + ", " + token.toker_name + ")")
    _____.end()
    _.end()


def tokeInspector(token):

    _.function(token.toker_name, "toke")


    _.end()