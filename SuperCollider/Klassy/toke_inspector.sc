// ==============
// toke_inspector
// ==============
//
// Here we pick the values out of the tokens
// and set its attributes appropriately
/*
Toke_Int {
    | toke |
    toke.val = toke.lexeme.asInteger;
}

Toke_Float {
    | toke |
    toke.val = toke.lexeme.asFloat;
}

Toke_Carrots {
    | toke |
    toke.val = toke.lexeme.size;
}

Toke_Tuplet {
    | toke |
    toke.val = 0;
}

Toke_Crotchets {
    | toke |
    toke.is_rest = true;
    toke.val = 2 ** toke.lexeme.size;
}

Toke_Quavers {
    | toke |
    toke.is_rest = true;
    // len("oo/")
    toke.val = 2 ** -(toke.lexeme.size - 1);
}

Toke_DynPiano {
    | toke |
    toke.val = 0;


}

Toke_DynForte {
    | toke |
    toke.val = 0;


}

Toke_Quarters {
    | toke |
    toke.is_rest = False;
    toke.val = 2 ** (toke.lexeme.size - 1);


}

Toke_Eighths {
    | toke |
    toke.is_rest = False;
    toke.val = 2 ** -toke.lexeme.size;
}



Toke_VectorNoat {
    | toke |

    var vector_noat_regex = "(~?)([a-g])(?:(//*)|(b*))(~?)";
    var s = toke.lexeme;
    var r = s.findRegexp(vector_noat_regex);

    if r.group(1) {
        toke.up = true;
    }

    toke.letter = r.group(2);

    sharps = r.group(3);
    if sharps {
        toke.sharps = sharps.size;
    }

    flats = r.group(4);
    if flats {
        toke.flats = flats.size;
    };

    if r.group(5) {
        if toke.up {
            SkoarError("Can't noat up and down: " ++ s).throw;
        toke.down = true;
    };

}

Toke_BooleanOp {
    | toke |
    toke.val = toke.lexeme;


}

Toke_Choard {
    | toke |
    toke.val = toke.lexeme;


}

Toke_MsgName {
    | toke |
    toke.val = toke.lexeme;


}

Toke_MsgNameWithArgs {
    | toke |
    toke.val = toke.lexeme.rstrip("<");


}

Toke_Volta {
    | toke |
    toke.val = int(toke.lexeme.strip("[.]"));


}

Toke_Symbol {
    | toke |
    toke.val = toke.lexeme[1:];


}

Toke_Segno {
    | toke |
    a = toke.lexeme.split("_");
    if len(a) > 1:
        toke.label = a[1];
    else:
        toke.label = "";


}

Toke_String {
    | toke |
    toke.val = toke.lexeme[1:-1];


}

Toke_Bars {
    | toke |
    toke.pre_repeat = toke.lexeme.startswith(":");
    toke.post_repeat = toke.lexeme.endswith(":");
    toke.unspent = True;



*/