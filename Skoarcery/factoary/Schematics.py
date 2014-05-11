from Skoarcery import terminals


def skoarToke(tongue):
    _ = _____ = _________ = _____________ = tongue

    _.cmt_hdr("Abstract Token")

    _.abstract_class("SkoarToke")
    _____.var("<", "lexeme")
    _____.classvar("<", "regex", _.null)
    _____.classvar("<", "inspectable", _.false)
    _____.newline()

    _____.constructor("s")
    _________.stmt(_.v_attr("lexeme") + " = s")
    _____.end_block()

    _____.cmt("how many characters to burn from the buffer")
    _____.method("burn")
    _________.return_(_.v_length(_.v_attr("lexeme")))
    _____.end_block()

    _____.cmt("override and return " + _.null + " for no match, new toke otherwise")
    _____.abstract_static_method("match", "buf", "offs")
    _________.throw("SubclassResponsibilityError", '"What are you doing human?"')
    _____.end_block()

    _____.cmt("match requested toke")
    _____.static_method("match_toke", "buf", "offs", "toke_class")
    _________.find_regex("match", "toke_class.regex", "buf", "offs")

    _________.if_(_.v_match("match"))
    _____________.return_(_.v_new("toke_class", _.v_regex_group_zero("match")))
    _________.end_if()

    _________.return_(_.null)
    _____.end_block()

    _.end_block()


def typical_token(tongue, token):
    _ = _____ = _________ = _____________ = tongue

    inspectable = _.true if token.name in terminals.inspectables else _.false

    _.class_(token.toker_name, "SkoarToke")

    _____.classvar("<", "regex", _.v_def_regex(token.regex))
    _____.classvar("<", "inspectable", inspectable)
    _____.newline()

    _____.static_method("match", "buf", "offs")
    _________.return_("SkoarToke.match_toke(buf, offs, " + token.toker_name + ")")
    _____.end_block()

    _.end_block()


