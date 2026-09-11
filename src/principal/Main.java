package principal;

import vista.VentanaEscaneo;
import modelo.EscanerRed;
import controlador.EscaneoController;

public class Main {
    public static void main(String[] args) {
        VentanaEscaneo vista = new VentanaEscaneo();
        EscanerRed escaner = new EscanerRed();
        EscaneoController controlador = new EscaneoController(vista, escaner);

        vista.setVisible(true);
    }
}
