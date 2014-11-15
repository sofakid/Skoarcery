from Skoarcery import terminals, emissions

# underskoarcery :P
_ = _____ = _________ = _____________ = _________________ = _____________________ = None

# -------
# Symbols
# -------
SkoarToke_ = "SkoarToke"
lexeme_ = "lexeme"
regex_ = "regex"
size_ = "size"
inspectable_ = "inspectable"
burn_ = "burn"
match_ = "match"
buf_ = "buf"
offs_ = "offs"
toke_class_ = "toke_class"
match_toke_ = "match_toke"
s_ = "s"
n_ = "n"
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

    regex = toke_class_ + "." + regex_

    _.cmt_hdr("Abstract Token")

    _.abstract_class(SkoarToke_)

    _____.attrvar("<", lexeme_)
    _____.attrvar("", size_)

    _____.classvar("<", regex_, _.null)
    _____.nl()

    _____.constructor(s_, n_)
    _________.stmt(_.v_ass(_.v_attr(lexeme_), s_))
    _________.stmt(_.v_ass(_.v_attr(size_), n_))
    _____.end()

    _____.cmt("how many characters to burn from the buffer")
    _____.method(burn_)
    _________.return_(size_)
    _____.end()

    _____.cmt("override and return " + _.null + " for no match, new toke otherwise")
    _____.abstract_static_method(match_, buf_, offs_)
    _________.throw(SubclassResponsibilityError_, _.v_str("What are you doing human?"))
    _____.end()

    _____.cmt("match requested toke")
    _____.static_method(match_toke_, buf_, offs_, toke_class_)
    _________.var(match_)

    if isinstance(_, emissions.ScTongue):
        _________.find_regex(match_, regex, buf_, offs_)

        _________.if_(match_ + ".isNil")
        _____________.return_(_.null)
        _________.end_if()

        _________.return_(_.v_new(toke_class_, "match[0]", "match[1]"))

    else:
        _____.try_()
        _________.find_regex(match_, regex, buf_, offs_)

        _________.return_(_.v_new(toke_class_, _.v_regex_group_zero(match_)))

        _____.except_any()
        _________.nop()
        _____.end()

        _____.return_(_.null)

    _____.end()
    _.end()


def whitespace_token():

    Whitespace = terminals.Whitespace
    regex = _.v_def_regex(Whitespace.regex)

    _.cmt_hdr("Whitespace is special")
    _.class_(Whitespace.toker_name, SkoarToke_)
    _____.classvar("<", regex_, regex)
    _____.nl()
    _____.static_method(burn_, buf_, offs_)
    _________.var(match_)

    if isinstance(_, emissions.ScTongue):
        _________.find_regex(match_, Whitespace.toker_name + "." + regex_, buf_, offs_)
        _________.if_(match_ + " != " + _.null)
        _____________.return_("match[1]")
        _________.end_if()

    else:
        _____.try_()
        _________.find_regex(match_, Whitespace.toker_name + "." + regex_, buf_, offs_)
        _________.return_(_.v_length(_.v_regex_group_zero(match_)))
        _____.except_any()
        _________.nop()
        _____.end()

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

    #inspectable = _.true if token.name in terminals.inspectables else _.false

    _.class_(token.toker_name, SkoarToke_)
    _____.classvar("<", regex_, _.v_def_regex(token.regex))
    #_____.classvar("<", inspectable_, inspectable)
    _____.nl()
    _____.static_method(match_, buf_, offs_)
    _________.return_(SkoarToke_ + "." + match_toke_ + "(" + buf_ + ", " + offs_ + ", " + token.toker_name + ")")
    _____.end()
    _.end()

