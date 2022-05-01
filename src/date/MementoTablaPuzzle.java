package date;

import java.util.Vector;

public class MementoTablaPuzzle {
	private Vector<CelulaPuzzle> tabla_memento;
	private int dim_puzzle_memento;
	
	public MementoTablaPuzzle() {}

	public MementoTablaPuzzle(Vector<CelulaPuzzle> tabla_memento, int dim_puzzle_memento) {
		this.tabla_memento = tabla_memento;
		this.dim_puzzle_memento = dim_puzzle_memento;
	}

	public Vector<CelulaPuzzle> getTabla_memento() {
		return tabla_memento;
	}

	public void setTabla_memento(Vector<CelulaPuzzle> tabla_memento) {
		this.tabla_memento = tabla_memento;
	}

	public int getDim_puzzle_memento() {
		return dim_puzzle_memento;
	}

	public void setDim_puzzle_memento(int dim_puzzle_memento) {
		this.dim_puzzle_memento = dim_puzzle_memento;
	}
		
}
