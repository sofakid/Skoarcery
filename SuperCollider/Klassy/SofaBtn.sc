SofaBtn {

    var <>lit;
    var <>label;
    var <>inst;

    var <device;

    var <>noteOn, <>noteOff;

    init {
        | device, label |
        this.device = device;
        this.label = label;
        this.lit = False;

        // default handlers just bubble up to device
        this.noteOn = {
            | note, vel |
            device.noteOn(note, vel);
        };

        this.noteOff = {
            | note |
            device.noteOff(note);
        };
    }
}
