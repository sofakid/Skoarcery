SofaMidi {

    classvar <>defaultVoicer;
    var <>inst;

    var <keys;

    var <>onProgramChange;

    *new {
        ^super.new.init()
    }

    init {
        onProgramChange = { |program| }
    }

    *initClass {

        //SofaMidi.defaultVoicer = Voicer(2, Instr(\default), [\env, Env.adsr(0.01, 0.2, 0.75, 0.1), \rq, 0.2]);

    }

    programChange { |program| }


}
