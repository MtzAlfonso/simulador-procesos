package simulador;

public class Localidad {
    private boolean visitada;
    private boolean ocupada;
    public int contenido;

    public Localidad(){
        this.visitada = false;
        this.ocupada = false;
        this.contenido = 0;
    }

    public boolean isVisitada() {
        return visitada;
    }

    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }
}
