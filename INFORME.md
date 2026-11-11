# Informe breve: RedSegura Sincelejo

**Autor:** TU NOMBRE

## 1. Decisiones de diseño
- **MixedGraph con lista de conexiones y mapa de vértices.** Un solo grafo admite conexiones dirigidas y no dirigidas, cada una con ID, etiqueta, peso y orientación. Una conexión no dirigida se trata como bidireccional al consultar vecinos.
- **Validaciones en el modelo.** Vertex y Edge rechazan IDs vacíos y pesos cero, negativos o no finitos. MixedGraph rechaza IDs duplicados y conexiones con vértices inexistentes.
- **Árboles con la etiqueta del vértice como clave** (comparación sin distinguir mayúsculas). Ambos árboles cuentan los pasos de la última búsqueda para comparar ABB y AVL.
- **RedSeguraCore como fachada.** La aplicación no toca el grafo ni los árboles directamente.
- **Bloqueo reversible.** Q6 quita R16, recalcula y la restaura, para no alterar otras consultas.

## 2. Complejidades
Ver la tabla del README.

## 3. Resultados de control
- S02 → S08 = 24 min (S02, S01, S07, S08).
- Bloqueando R16: S02, S01, S09, S10, S08 = 24 min. El camino cambia pero el costo empata.
- BFS y DFS desde S02 visitan los 12 vértices en distinto orden: BFS usa cola y avanza por niveles; DFS se va por una rama hasta el fondo antes de retroceder.
- Q1: Hospital Universitario se encuentra en 3 pasos en el ABB (altura 6) y en 1 paso en el AVL (altura 4).

## 4. Medición ABB vs AVL (clase BenchmarkArboles)
Con claves insertadas en orden creciente, el ABB se degenera en una lista y el AVL se mantiene balanceado.

| n | ABB altura | ABB pasos | AVL altura | AVL pasos |
|---|---|---|---|---|
| 250 | 250 | 250 | 8 | 8 |
| 500 | 500 | 500 | 9 | 9 |
| 1000 | 1000 | 1000 | 10 | 10 |
| 2000 | 2000 | 2000 | 11 | 11 |

## 5. Error encontrado y corregido
Al escribir las pruebas se encontró que `getPathCost` no reconocía las conexiones no dirigidas recorridas en sentido contrario al declarado (por ejemplo S10 → S09 por R08). Se corrigió aceptando ambos sentidos cuando la conexión no es dirigida, y se agregó una prueba que lo cubre.

## 6. Limitaciones
Los pesos son tiempos de simulación, no datos oficiales de tránsito.
