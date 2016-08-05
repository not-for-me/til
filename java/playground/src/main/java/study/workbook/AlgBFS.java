package study.workbook;

import java.util.Scanner;

/**
 *
 */
public class AlgBFS {
	public static void main(String... args) {
		Scanner sc = new Scanner(System.in);
		String[] readData = sc.nextLine().split(" ");
		final int COLUMN = Integer.parseInt(readData[0]);
		final int ROW = Integer.parseInt(readData[1]);
		int graph[][] = new int[ROW * COLUMN][ROW * COLUMN];
//		IntStream.range(0, ROW * COLUMN).forEach(row -> IntStream.range(0, ROW * COLUMN).forEach(col -> graph[row][col] = -1));

		int inputInfo[][] = new int[ROW][COLUMN];
		for (int row = 0; row < ROW; row++) {
			String readRow = sc.nextLine();
			for (int col = 0; col < COLUMN; col++) {
				inputInfo[row][col] = Character.digit(readRow.charAt(col), 10);
			}
		}

		// Test if inputInfo is right
		/*
		for (int i = 0; i < ROW +2; i++) {
			for (int j = 0; j < COLUMN + 2; j++) {
				System.out.print(inputInfo[i][j] + " ");
			}
			System.out.println("");
		}
		*/

		for (int row = 0; row < ROW; row++) {
			for (int col = 0; col < COLUMN; col++) {
				int edgeNo = row * COLUMN + col;

				int lowerEdgeNo = ( row + 1 ) * COLUMN + col;
				if (-1 < lowerEdgeNo && lowerEdgeNo < ROW * COLUMN && row + 1 < ROW) {
					graph[edgeNo][lowerEdgeNo] = inputInfo[row + 1][col];
					graph[lowerEdgeNo][edgeNo] = inputInfo[row + 1][col];
				}

				int rightEdgeNo = row * COLUMN + ( col + 1 );
				if (-1 < rightEdgeNo && rightEdgeNo < ROW * COLUMN && col + 1 < COLUMN) {
					graph[edgeNo][rightEdgeNo] = inputInfo[row][col + 1];
					graph[rightEdgeNo][edgeNo] = inputInfo[row][col + 1];
				}
				graph[edgeNo][edgeNo] = 0;
			}
		}

		// Test if graph is right
		for (int i = 0; i < ROW * COLUMN; i++) {
			for (int j = 0; j < ROW * COLUMN; j++) {
				System.out.print(graph[i][j] + " ");
			}
			System.out.println("");
		}

		AlgBFS bfs = new AlgBFS();
		System.out.println(bfs.useDijkstra(ROW * COLUMN, 0, graph));

	}

	private int useDijkstra(int vertexNum, int sourceVertex, int[][] graph) {
		// 1. Initialized shortest path set and distance array with INFINITE
		boolean spSet[] = new boolean[vertexNum];
		int distance[] = new int[vertexNum];
		for (int i = 0; i < vertexNum; i++) {
			spSet[i] = false;
			distance[i] = Integer.MAX_VALUE;
		}

		distance[sourceVertex] = 0;
		for (int i = 0; i < vertexNum; i++) {
			int minDistance = Integer.MAX_VALUE;
			int minIndex = -1;
			// 2. Pickup the minimum distance vertex size
			for (int v = 0; v < vertexNum; v++) {
				if (!spSet[v] && distance[v] < minDistance) {
					minDistance = distance[v];
					minIndex = v;
				}
			}
			spSet[minIndex] = true;

			for (int v = 0; v < vertexNum; v++) {
				boolean isNotSelf = v != minIndex;
				boolean isConnected = graph[minIndex][v] != -1;
				boolean islessThanDistance = distance[minIndex] + graph[minIndex][v] <
						distance[v];
				if (!spSet[v] && isNotSelf && isConnected && islessThanDistance) {
					distance[v] = distance[minIndex] + graph[minIndex][v];
				}
			}
		}

		return distance[vertexNum - 1];
	}
}
