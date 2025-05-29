public class ListaFelinos {
    NodoFelino head;

    // Zonas predefinidas
    public static String[] zonas = {
            "Tercer Planeta", "Fortaleza Felina", "Desierto de la Muerte",
            "Montañas del Trueno", "Valle de las Sombras"
    };

    // Inserta ordenadamente por ID si no existe
    public boolean insertarOrdenado(FuerzaFelina f) {
        if (buscar(f.id) != null) return false;

        NodoFelino nuevo = new NodoFelino(f);

        if (head == null || f.id < head.data.id) {
            nuevo.next = head;
            if (head != null) head.prev = nuevo;
            head = nuevo;
            return true;
        }

        NodoFelino actual = head;
        while (actual.next != null && actual.next.data.id < f.id) {
            actual = actual.next;
        }

        nuevo.next = actual.next;
        if (actual.next != null) actual.next.prev = nuevo;
        actual.next = nuevo;
        nuevo.prev = actual;

        return true;
    }

    // Busca un felino por ID usando búsqueda binaria
    public FuerzaFelina buscar(int id) {
        return buscarBinario(id, head, getLast());
    }

    // Obtiene el último nodo
    private NodoFelino getLast() {
        NodoFelino aux = head;
        if (aux == null) return null;
        while (aux.next != null) aux = aux.next;
        return aux;
    }

    // Búsqueda binaria sobre lista doblemente enlazada
    private FuerzaFelina buscarBinario(int id, NodoFelino left, NodoFelino right) {
        if (left == null || right == null || left.data.id > right.data.id) return null;

        NodoFelino mid = left;
        int len = 0;
        NodoFelino temp = left;

        while (temp != right.next) {
            len++;
            temp = temp.next;
        }

        for (int i = 0; i < len / 2; i++) {
            if (mid != null) mid = mid.next;
        }

        if (mid == null) return null;
        if (mid.data.id == id) return mid.data;
        if (id < mid.data.id) return buscarBinario(id, left, mid.prev);
        else return buscarBinario(id, mid.next, right);
    }

    // Filtra por destreza y ordena por nivel de combate descendente
    public ListaFelinos filtrarYOrdenar(String destreza) {
        ListaFelinos nueva = new ListaFelinos();
        NodoFelino aux = head;

        while (aux != null) {
            if (aux.data.destreza.equals(destreza)) {
                FuerzaFelina copia = new FuerzaFelina(
                        aux.data.id, aux.data.alias, aux.data.destreza, aux.data.combate, aux.data.zona
                );
                nueva.insertarOrdenado(copia); // mantiene orden por ID al inicio
            }
            aux = aux.next;
        }

        nueva.ordenarPorNivelDescendente();
        return nueva;
    }

    // Ordena por nivel de combate de mayor a menor (selección)
    private void ordenarPorNivelDescendente() {
        for (NodoFelino i = head; i != null; i = i.next) {
            NodoFelino max = i;
            for (NodoFelino j = i.next; j != null; j = j.next) {
                if (j.data.combate > max.data.combate) {
                    max = j;
                }
            }

            if (max != i) {
                FuerzaFelina temp = i.data;
                i.data = max.data;
                max.data = temp;
            }
        }
    }

    // Cuenta cuántos hay por cada zona usando recursión
    public String contarPorZona() {
        return contarPorZonaRec(zonas, 0);
    }

    private String contarPorZonaRec(String[] zonas, int index) {
        if (index >= zonas.length) return "";
        int count = contarZonaEnLista(head, zonas[index]);
        return zonas[index] + ": " + count + "\n" + contarPorZonaRec(zonas, index + 1);
    }

    // Cuenta recursivamente cuántos nodos tienen esa zona
    private int contarZonaEnLista(NodoFelino nodo, String zona) {
        if (nodo == null) return 0;
        int actual = nodo.data.zona.equals(zona) ? 1 : 0;
        return actual + contarZonaEnLista(nodo.next, zona);
    }
}
