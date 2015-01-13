SkoarFairy {

    var <name;
    var <minstrel;
    var <impression;

    *new {
        | nom, m |
        "new SkoarFairy: ".post; nom.postln;
        ^super.new.init(nom, m);
    }

    init {
        | nom, m |
        name = nom;
        minstrel = m;
        impression = nil; // todo
    }

    impress {
        | x |
        ("$:" ++ name ++ ".impression: " ++ x.asString).postln;
        impression = Skoarpuscle.wrap(x);
        ^impression;
    }

}
