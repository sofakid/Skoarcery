// ==============
// toke_inspector
// ==============
//
// Here we pick the values out of the tokens
// and set its attributes appropriately

SkoarTokeInspector {

    *new {

        var dict = Dictionary[
        
            "Toke_Int" -> {
                | toke |
                toke.val = toke.lexeme.asInteger;
            },
            
            "Toke_Float" -> {
                | toke |
                toke.val = toke.lexeme.asFloat;
            },
            
            "Toke_Carrots" -> {
                | toke |
                toke.val = toke.lexeme.size;
            },
            
            "Toke_Tuplet" -> {
                | toke |
                toke.val = 0;
            },
            
            "Toke_Crotchets" -> {
                | toke |
                toke.is_rest = true;
                toke.val = 2 ** toke.lexeme.size;
            },
            
            "Toke_Quavers" -> {
                | toke |
                toke.is_rest = true;
                // len("oo/")
                toke.val = 2 ** (-1 * (toke.lexeme.size - 1));
            },
            
            "Toke_DynPiano" -> {
                | toke |
                toke.val = 0;
            },
            
            "Toke_DynForte" -> {
                | toke |
                toke.val = 0;
            },
            
            "Toke_Quarters" -> {
                | toke |
                toke.is_rest = false;
                toke.val = 2 ** (toke.lexeme.size - 1);
            },
            
            "Toke_Eighths" -> {
                | toke |
                toke.is_rest = false;
                toke.val = 2 ** (-1 * toke.lexeme.size);
            },
            
            "Toke_VectorNoat" -> {
                | toke |
            
                var vector_noat_regex = "[a-g]";
                var s = toke.lexeme;
                var r = s.findRegexp(vector_noat_regex);

                toke.val = r[0][1];

            },
            
            "Toke_BooleanOp" -> {
                | toke |
                toke.val = toke.lexeme;
            },
            
            "Toke_Choard" -> {
                | toke |
                toke.val = toke.lexeme;
            },
            
            "Toke_MsgName" -> {
                | toke |
                toke.val = toke.lexeme;
            },
            
            "Toke_MsgNameWithArgs" -> {
                | toke |
                var x = "<";
                var s = toke.lexeme;

                if (s.last == x) {
                    s = s.copyRange(0, s.size - 2);
                };

                toke.val = s;
            },
            
            "Toke_Volta" -> {
                | toke |
                toke.val = int(toke.lexeme.strip("[.]"));
            },
            
            "Toke_Symbol" -> {
                | toke |
                toke.val = toke.lexeme;
            },
            
            "Toke_Segno" -> {
                | toke |
                var a = toke.lexeme.split("_");
                if (a.size > 1) {
                    toke.label = a[1];
                } {
                    toke.label = "";
                };
            },
            
            "Toke_String" -> {
                | toke |
                toke.val = toke.lexeme;
            },
            
            "Toke_Bars" -> {
                | toke |
                toke.pre_repeat = toke.lexeme.beginsWith(":");
                toke.post_repeat = toke.lexeme.endsWith(":");
                toke.unspent = true;
            }
        ];        
        ^dict;
    }

}