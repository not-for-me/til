package study.workbook.test;

import java.util.Scanner;

public class Main {
	public static void main(String... args) {
		Scanner sc = new Scanner(System.in);
		String[] readData = sc.nextLine().split(" ");
		final byte COLUMN = Byte.parseByte(readData[0]);
		final byte ROW = Byte.parseByte(readData[1]);

		if (1 <= ROW && ROW <= 100 && 1 <= COLUMN && COLUMN <= 100) {
			byte graph[][] = new byte[ROW * COLUMN][ROW * COLUMN];

			byte inputInfo[][] = new byte[ROW][COLUMN];
			for (byte row = 0; row < ROW; row++) {
				String readRow = sc.nextLine();
				for (byte col = 0; col < COLUMN; col++) {
					inputInfo[row][col] = (byte) Character.digit(readRow.charAt(col), 10);
				}
			}

			for (byte row = 0; row < ROW; row++) {
				for (byte col = 0; col < COLUMN; col++) {
					short edgeNo = (short) (row * COLUMN + col);

					short upperEdgeNo = (short) ((row - 1) * COLUMN + col);
					if (-1 < upperEdgeNo && upperEdgeNo < ROW * COLUMN && -1 < row - 1) {
						graph[edgeNo][upperEdgeNo] = (byte) (inputInfo[row - 1][col] == 0 ? 1 : 2);
					}

					short rightEdgeNo = (short) (row * COLUMN + (col + 1));
					if (-1 < rightEdgeNo && rightEdgeNo < ROW * COLUMN && col + 1 < COLUMN) {
						graph[edgeNo][rightEdgeNo] = (byte) (inputInfo[row][col + 1] == 0 ? 1 : 2);
					}

					short lowerEdgeNo = (short) ((row + 1) * COLUMN + col);
					if (-1 < lowerEdgeNo && lowerEdgeNo < ROW * COLUMN && row + 1 < ROW) {
						graph[edgeNo][lowerEdgeNo] = (byte) (inputInfo[row + 1][col] == 0 ? 1 : 2);
					}

					short leftEdgeNo = (short) (row * COLUMN + (col - 1));
					if (-1 < leftEdgeNo && leftEdgeNo < ROW * COLUMN && -1 < col - 1) {
						graph[edgeNo][leftEdgeNo] = (byte) (inputInfo[row][col - 1] == 0 ? 1 : 2);
					}

					graph[edgeNo][edgeNo] = 0;
				}
			}

			// 1. Initialized byteest path set and distance array with INFINITE
			boolean spSet[] = new boolean[ROW * COLUMN];
			byte distance[] = new byte[ROW * COLUMN];
			for (short i = 0; i < ROW * COLUMN; i++) {
				spSet[i] = false;
				distance[i] = Byte.MAX_VALUE;
			}

			distance[0] = 0;
			for (short i = 0; i < ROW * COLUMN; i++) {
				byte minDistance = Byte.MAX_VALUE;
				short minIndex = -1;
				// 2. Pickup the minimum distance vertex size
				for (short v = 0; v < ROW * COLUMN; v++) {
					if (!spSet[v] && distance[v] < minDistance) {
						minDistance = distance[v];
						minIndex = v;
					}
				}
				spSet[minIndex] = true;

				for (short v = 0; v < ROW * COLUMN; v++) {
					boolean isNotSelf = v != minIndex;
					boolean isConnected = graph[minIndex][v] != 0;
					boolean isLessThanDistance = distance[minIndex] + graph[minIndex][v] < distance[v];
					if (!spSet[v] && isNotSelf && isConnected && isLessThanDistance) {
						distance[v] = (byte) (distance[minIndex] + graph[minIndex][v]/2);
					}
				}
			}
			System.out.println(distance[ROW * COLUMN - 1]);
		}
	}
}
