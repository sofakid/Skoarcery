/*




       E: T
       T: T { '+' F } | F
       F: F { '*' I } | I
       I: <identifier>

typedef struct _exptree exptree;
 struct _exptree {
     char token;
     exptree *left;
     exptree *right;
 };


 // E: T
 exptree *parse_e(void)
 {
     return parse_t();
 }

 // T: T { '+' F } | F
 exptree *parse_t(void)
 {
     exptree *firToke_f = parse_f();

     while(cur_token() == '+') {
         exptree *replace_tree = alloc_tree();
         replace_tree->token = cur_token();
         replace_tree->left = firToke_f;
         next_token();
         replace_tree->right = parse_f();
         firToke_f = replace_tree;
     }

     return firToke_f;
 }

 // F: F { '*' I } | I
 exptree *parse_f(void)
 {
     exptree *firToke_i = parse_i();

     while(cur_token() == '*') {
         exptree *replace_tree = alloc_tree();
         replace_tree->token = cur_token();
         replace_tree->left = firToke_i;
         next_token();
         replace_tree->right = parse_i();
         firToke_i = replace_tree;
     }

     return firToke_i;
 }

 // I: <identifier>
 exptree *parse_i(void)
 {
     exptree *i = alloc_tree();
     i->left = i->right = NULL;
     i->token = cur_token();
     next_token();
     return i;
 }
*/

SkoarNode {

    var <token;
    var left;
    var right;

    *new {
        | token, left=nil, right=nil |
        ^super.new.init( token, left, right )
    }

    init {
        | token, left, right |

        this.left = left;
        this.token = token;
        this.right = right;
    }

}

SkoarParser {

    var <buf;
    var offs;

    *new {
        | in |
        ^super.new.init( in )
    }

    init {
        | in |

        this.buf = in;
        this.j = 0;
    }

    toke {
        | toke |

        // let's get a little more offset
        offs += toke.burn;

        // mmm.. mountain-fresh..
        offs += Toke_Whitespace.burn;

    }



    /*

    - Parse the next level of the grammar and get its output tree, designate it the first tree, F
    - While there is terminating token, T, that can be put as the parent of this node:
      - Allocate a new node, N
      - Set N's current operator as the current input token
      - Advance the input one token
      - Set N's left subtree as F
      - Parse another level down again and store this as the next tree, X
      - Set N's right subtree as X
      - Set F to N
    - Return N

    */
    // skoar =: time_sig? bar+
    //
    // skoar: time_sig bars | bars
    // bars:  bars bar | bar
    // bar: BAR_SEP.auto_goto?.phrasey* | phrasey*
    pSkoar {
        ^this.pBars();
    }

    pBars {
        var first;

        first = this.pBar();

        // ???

        ^this.pBars();
    }

    pBar {

    }

    // phrasey =: SYMBOL | key_set | note | beat | neg_beat | tickle | goto_dst | goto
    pPhrasey {

        var x;

        if (x = Toke_Symbol.match(buf, offs)) {
        }

      /*  x = case
        { i == 1 }   { \no }
        { i == 1.1 } { \wrong }
        { i == 1.3 } { \wrong }
        { i == 1.5 } { \wrong }
        { i == 2 }   { \wrong }
        { i == 0 }   { \true };
*/
    }

}

SkoarToke {

    var <buf;
    classvar <regex = nil;

    *new {
        | s |
        ^super.new.init( s )
    }

    init {
        | s |
        buf = s;
    }

    // how many characters to burn from the buffer
    burn {^buf.size}

    // override and return nil for no match, new toke otherwise
    *match {}

    *match_toke {
        | buf, offs, toke_class |

        var o = buf.findRegexp(toke_class.regex, offs);

        if (o.size > 0) {
            ^SkoarTokeclass.new(o[0][1]);
        }

        ^nil
    }

}

Toke_Whitespace : SkoarToke {
    classvar <regex = "^\s*";

    *burn {
        | buf, offs |

        var o = buf.findRegexp("^\s*", offs);

        if (o.size > 0) {
            ^o[0][1].size;
        }

        ^0
    }
}

Toke_NegQBeat : SkoarToke {
    classvar <regex = "^\.?[\[]*[\(]+";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_NegQBeat) }
}

Toke_NegEBeat : SkoarToke {
    classvar <regex = "^\.?[\[]+";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_NegEBeat) }
}

Toke_QBeat : SkoarToke {
    classvar <regex = "^[\)]+[\]]*\.?";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_QBeat) }
}

Toke_EBeat : SkoarToke {
    classvar <regex = "^[\]]+\.?";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_EBeat) }
}

Toke_Slide : SkoarToke {
    classvar <regex = "^\+\+";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_Slide) }
}

Toke_Bar : SkoarToke {
    classvar <regex = "^[|]";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_Bar) }
}

Toke_GotoDst : SkoarToke {
    classvar <regex = "^([a-z]+[a-zA-Z0-9_]*)?:";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_GotoDst) }
}

Toke_Goto : SkoarToke {
    classvar <regex = "^:";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_Goto) }
}

Toke_NoteLetter : SkoarToke {
    classvar <regex = "^[a-g]";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_NoteLetter) }
}

Toke_Sharps : SkoarToke {
    classvar <regex = "^#+";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_Sharps) }
}

Toke_Flats : SkoarToke {
    classvar <regex = "^b+";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_Flats) }
}

Toke_NoteOctave : SkoarToke {
    classvar <regex = "^0|([1-9][0-9]*)";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_NoteOctave) }
}

Toke_Symbol : SkoarToke {
    classvar <regex = "^\\[a-zA-Z][a-zA-Z0-9_]*";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_Symbol) }
}

Toke_Args : SkoarToke {
    classvar <regex = "^<[^>]*>";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_Args) }
}

Toke_ListSep : SkoarToke {
    classvar <regex = "^,";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_ListSep) }
}

Toke_On : SkoarToke {
    classvar <regex = "^[+]";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_On) }
}

Toke_Off : SkoarToke {
    classvar <regex = "^-";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_Off) }
}

Toke_Toggle : SkoarToke {
    classvar <regex = "^!";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_Toggle) }
}

Toke_Assign : SkoarToke {
    classvar <regex = "^=";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_Assign) }
}

Toke_Label : SkoarToke {
    classvar <regex = "^[a-zA-Z]";
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_Assign) }
}






