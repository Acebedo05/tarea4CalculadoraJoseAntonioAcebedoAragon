package daw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author acebedo
 */
public class PanelPrincipal extends JPanel implements ActionListener {

    private PanelBotones botonera;
    private JTextArea areaTexto;

    private String operador;
    private Double primerNumero;

    public PanelPrincipal() {
        initComponents();
        resetearCalculadora();
    }

    private void initComponents() {
        botonera = new PanelBotones();
        areaTexto = new JTextArea(10, 50);
        areaTexto.setEditable(false);
        areaTexto.setBackground(Color.white);

        this.setLayout(new BorderLayout());
        this.add(areaTexto, BorderLayout.NORTH);
        this.add(botonera, BorderLayout.SOUTH);

        for (JButton boton : botonera.getgrupoBotones()) {
            boton.addActionListener(this);
        }
    }

    private void resetearCalculadora() {
        operador = "";
        primerNumero = null;
        areaTexto.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object o = ae.getSource();

        if (o instanceof JButton) {
            JButton botonPresionado = (JButton) o;
            String textoBoton = botonPresionado.getText();

            if (esNumero(textoBoton)) {
                if (operador.isEmpty()) {
                    areaTexto.append(textoBoton);
                } else {
                    if (primerNumero == null) {
                        primerNumero = Double.parseDouble(areaTexto.getText());
                        areaTexto.setText(textoBoton);
                    } else {
                        areaTexto.append(textoBoton);
                    }
                }
            } else if (esOperador(textoBoton)) {
                if (primerNumero == null) {
                    primerNumero = Double.parseDouble(areaTexto.getText());
                    operador = textoBoton;
                    areaTexto.setText("");
                } else if (!operador.isEmpty()) {
                    operador = textoBoton;
                    areaTexto.setText("");
                }
            } else if (textoBoton.equals("=")) {
                if (primerNumero != null && !operador.isEmpty() && !areaTexto.getText().isEmpty()) {
                    Double segundoNumero = Double.parseDouble(areaTexto.getText());
                    double resultado = ejecutarOperacion(primerNumero, segundoNumero, operador);
                    areaTexto.setText(String.valueOf(resultado));
                    operador = "";
                    primerNumero = resultado;
                } else {
                    System.out.println("Error: Operación incompleta");
                }
            } else if (textoBoton.equals("C")) {
                resetearCalculadora();
            }
        }
    }

    private boolean esNumero(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean esOperador(String texto) {
        return texto.equals("+") || texto.equals("-") || texto.equals("*") || texto.equals("/");
    }

    private double ejecutarOperacion(double num1, double num2, String operador) {
        switch (operador) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    System.out.println("Error: No se puede dividir por cero");
                    return 0;
                }
                return num1 / num2;
            default:
                System.out.println("Error: Operación no posible");
                return 0;
        }
    }
}
