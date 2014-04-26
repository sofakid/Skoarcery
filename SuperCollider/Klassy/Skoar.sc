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

SkoarToke

