SkoarBoard {

    var data;

    init {
        data = IdentityDictionary.new;
    }

    put {
        | k, v |
        data[k] = v;
    }

    at {
        | k |
        ^data[k];
    }

    event {
        var e = (type: \note);

        ^data.transformEvent(e);
    }

}
