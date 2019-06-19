package br.pro.hashi.ensino.desagil.aps.model;

public class HalfAdder extends Gate {

    private final XorGate XOR;
    private final AndGate AND;

    public HalfAdder() {
        super("HalfAdder", 2, 2);
        XOR = new XorGate();
        AND = new AndGate();
    }

    @Override
    public boolean read(int outputPin) {
        // Conexões de Saída (Output)
        if (outputPin == 0) {
            return XOR.read();
        } else if (outputPin == 1) {
            return AND.read();
        } else {
            throw new IndexOutOfBoundsException(outputPin);
        }
    }

    @Override
    public void connect(int inputPin, SignalEmitter emitter) {
        switch (inputPin) {
            // Conexões de Entrada (Emitter)
            case 0:
                XOR.connect(0, emitter);
                AND.connect(0, emitter);
                break;
            case 1:
                XOR.connect(1, emitter);
                AND.connect(1, emitter);
                break;
            default:
                throw new IndexOutOfBoundsException(inputPin);

        }
    }
}
