//package compressors;
//
//import org.junit.Before;
//import org.junit.Test;
//
//
//import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertEquals;
//
//public  class TestHaarCompression {
//  HaarCompression haarCompression;
//
//  @Before
//  public void setUp() {
//    haarCompression = new HaarCompression();
//  }
//
//  @Test
//  public void testPadToSquareMatrix(){
//    double[][] matrix = new double[][]{{1.0, 2.1}, {3,4}, {5,6}};
//    double[][] paddedMatrix = HaarCompression.padToSquareMatrix(matrix);
//    double[][] expectedMatrix = new double[][]{{1.0, 2.1, 0,0}, {3, 4, 0,0}, {5
//            , 6,
//            0,0},{0,0,0,0}};
//    assertArrayEquals(expectedMatrix, paddedMatrix);
//  }
//
//
//  @Test
//  public void testTransform(){
//    double[] row = new double[]{5,3,2,4,2,1,0,3};
//    double[] expectedRowOne = new double[]{5.65685424949238,
//            4.242640687119285, 2.1213203435596424, 2.1213203435596424, 1.414213562373095, -1.414213562373095, 0.7071067811865475, -2.1213203435596424};
//    assertArrayEquals(expectedRowOne, HaarCompression.transform(row,8), 0.0001);
//    double[] expectedRowTwo = new double[]{6.999999999999998, 2.9999999999999996, 0.9999999999999999, 0.0};
//    assertArrayEquals(expectedRowTwo, HaarCompression.transform(expectedRowOne,4), 0.0001);
//  }
//
//  @Test
//  public void testHaar(){
//    double[][] data = new double[][]{{1,2},{3,4}};
//    double[][] expectedData =
//            new double[][]{{5.0, -0.9999999999999999}, {-2.0, 0.0}};
//    assertArrayEquals(expectedData, HaarCompression.haar(data));
//  }
//
//
//  @Test
//  public void testInvHaar(){
//    double[][] data =
//            new double[][]{{5.0, -0.9999999999999999}, {-2.0, 0.0}};
//    int[][] expectedData = new int[][]{{1,2},{3,4}};
//    double[][] actualData = HaarCompression.invhaar(data);
//    for (int i = 0; i < data.length; i++) {
//      for (int j = 0; j < data[0].length; j++) {
//        assertEquals(expectedData[i][j], Math.round(actualData[i][j]), 0.0001);
//      }
//    }
//  }
//
//
//
//}