/*
 * Copyright (c) 2009-2014, Peter Abeles. All Rights Reserved.
 *
 * This file is part of Efficient Java Matrix Library (EJML).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ejml.ops;

import org.ejml.data.CDenseMatrix64F;
import org.ejml.data.Complex64F;
import org.ejml.data.DenseMatrix64F;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Peter Abeles
 */
public class TestCCommonOps {

    Random rand = new Random(234);

    @Test
    public void convert() {
        DenseMatrix64F input = RandomMatrices.createRandom(5,7,-1,1,rand);
        CDenseMatrix64F output = new CDenseMatrix64F(5,7);

        Complex64F a = new Complex64F();

        CCommonOps.convert(input, output);

        for (int i = 0; i < input.numRows; i++) {
            for (int j = 0; j < input.numCols; j++) {
                output.get(i,j,a);

                assertEquals(input.get(i,j),a.getReal(),1e-8);
                assertEquals(0,a.getImaginary(),1e-8);
            }
        }
    }

    @Test
    public void stripReal() {
        CDenseMatrix64F input = CRandomMatrices.createRandom(5,7,-1,1,rand);
        DenseMatrix64F output = new DenseMatrix64F(5,7);

        Complex64F a = new Complex64F();

        CCommonOps.stripReal(input, output);

        for (int i = 0; i < input.numRows; i++) {
            for (int j = 0; j < input.numCols; j++) {
                input.get(i,j,a);

                assertEquals(a.getReal(),output.get(i,j),1e-8);
            }
        }
    }

    @Test
    public void stripImaginary() {
        CDenseMatrix64F input = CRandomMatrices.createRandom(5,7,-1,1,rand);
        DenseMatrix64F output = new DenseMatrix64F(5,7);

        Complex64F a = new Complex64F();

        CCommonOps.stripImaginary(input, output);

        for (int i = 0; i < input.numRows; i++) {
            for (int j = 0; j < input.numCols; j++) {
                input.get(i,j,a);

                assertEquals(a.getImaginary(),output.get(i,j),1e-8);
            }
        }
    }

    @Test
    public void magnitude() {
        CDenseMatrix64F input = CRandomMatrices.createRandom(5,7,-1,1,rand);
        DenseMatrix64F output = new DenseMatrix64F(5,7);

        Complex64F a = new Complex64F();

        CCommonOps.magnitude(input, output);

        for (int i = 0; i < input.numRows; i++) {
            for (int j = 0; j < input.numCols; j++) {
                input.get(i,j,a);

                assertEquals(a.getMagnitude(),output.get(i,j),1e-8);
            }
        }
    }

    @Test
    public void add() {
        CDenseMatrix64F matrixA = CRandomMatrices.createRandom(5,7,-1,1,rand);
        CDenseMatrix64F matrixB = CRandomMatrices.createRandom(5,7,-1,1,rand);
        CDenseMatrix64F out = CRandomMatrices.createRandom(5,7,-1,1,rand);

        Complex64F a = new Complex64F();
        Complex64F b = new Complex64F();
        Complex64F found = new Complex64F();
        Complex64F expected = new Complex64F();

        CCommonOps.add(matrixA, matrixB, out);

        for (int i = 0; i < matrixA.numRows; i++) {
            for (int j = 0; j < matrixA.numCols; j++) {
                matrixA.get(i,j,a);
                matrixB.get(i,j,b);
                out.get(i,j,found);

                ComplexMath64F.plus(a, b, expected);

                assertEquals(expected.real,found.real,1e-8);
                assertEquals(expected.imaginary,found.imaginary,1e-8);
            }
        }
    }

    @Test
    public void subtract() {
        CDenseMatrix64F matrixA = CRandomMatrices.createRandom(5,7,-1,1,rand);
        CDenseMatrix64F matrixB = CRandomMatrices.createRandom(5,7,-1,1,rand);
        CDenseMatrix64F out = CRandomMatrices.createRandom(5,7,-1,1,rand);

        Complex64F a = new Complex64F();
        Complex64F b = new Complex64F();
        Complex64F found = new Complex64F();
        Complex64F expected = new Complex64F();

        CCommonOps.subtract(matrixA, matrixB, out);

        for (int i = 0; i < matrixA.numRows; i++) {
            for (int j = 0; j < matrixA.numCols; j++) {
                matrixA.get(i,j,a);
                matrixB.get(i,j,b);
                out.get(i,j,found);

                ComplexMath64F.minus(a, b, expected);

                assertEquals(expected.real,found.real,1e-8);
                assertEquals(expected.imaginary,found.imaginary,1e-8);
            }
        }
    }

    @Test
    public void multiply() {
        fail("implement");
    }

    @Test
    public void transpose_one() {

        CDenseMatrix64F a = CRandomMatrices.createRandom(4,4,-1,1,rand);
        CDenseMatrix64F b = a.copy();

        CCommonOps.transpose(b);

        Complex64F found = new Complex64F();
        Complex64F expected = new Complex64F();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                a.get(i,j,expected);
                b.get(j,i,found);

                assertEquals(expected.real,found.real,1e-8);
                assertEquals(expected.imaginary,found.imaginary,1e-8);
            }
        }
    }

    @Test
    public void transpose_two() {

        CDenseMatrix64F a = CRandomMatrices.createRandom(4,5,-1,1,rand);
        CDenseMatrix64F b = CRandomMatrices.createRandom(5,4,-1,1,rand);

        CCommonOps.transpose(a, b);

        Complex64F found = new Complex64F();
        Complex64F expected = new Complex64F();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                a.get(i,j,expected);
                b.get(j,i,found);

                assertEquals(expected.real,found.real,1e-8);
                assertEquals(expected.imaginary,found.imaginary,1e-8);
            }
        }
    }

    @Test
    public void invert_2() {
        fail("implement");
    }

    @Test
    public void invert_3() {
        fail("implement");
    }

    @Test
    public void det() {
        fail("implement");
    }

    @Test
    public void elementMultiply() {
        CDenseMatrix64F in = CRandomMatrices.createRandom(5,7,-1,1,rand);
        CDenseMatrix64F out = CRandomMatrices.createRandom(5,7,-1,1,rand);

        Complex64F a = new Complex64F(1.2,-0.3);
        Complex64F b = new Complex64F();
        Complex64F found = new Complex64F();
        Complex64F expected = new Complex64F();

        CCommonOps.elementMultiply(in,a.real,a.imaginary,out);

        for (int i = 0; i < in.numRows; i++) {
            for (int j = 0; j < in.numCols; j++) {
                in.get(i,j,b);
                out.get(i,j,found);

                ComplexMath64F.multiply(a,b,expected);

                assertEquals(expected.real,found.real,1e-8);
                assertEquals(expected.imaginary,found.imaginary,1e-8);
            }
        }
    }

    @Test
    public void elementDivide_right() {
        CDenseMatrix64F in = CRandomMatrices.createRandom(5,7,-1,1,rand);
        CDenseMatrix64F out = CRandomMatrices.createRandom(5,7,-1,1,rand);

        Complex64F a = new Complex64F();
        Complex64F b = new Complex64F(1.2,-0.3);
        Complex64F found = new Complex64F();
        Complex64F expected = new Complex64F();

        CCommonOps.elementDivide(in,b.real,b.imaginary,out);

        for (int i = 0; i < in.numRows; i++) {
            for (int j = 0; j < in.numCols; j++) {
                in.get(i,j,a);
                out.get(i,j,found);

                ComplexMath64F.divide(a,b,expected);

                assertEquals(expected.real,found.real,1e-8);
                assertEquals(expected.imaginary,found.imaginary,1e-8);
            }
        }
    }

    @Test
    public void elementDivide_left() {
        CDenseMatrix64F in = CRandomMatrices.createRandom(5,7,-1,1,rand);
        CDenseMatrix64F out = CRandomMatrices.createRandom(5,7,-1,1,rand);

        Complex64F a = new Complex64F(1.2,-0.3);
        Complex64F b = new Complex64F();
        Complex64F found = new Complex64F();
        Complex64F expected = new Complex64F();

        CCommonOps.elementDivide(a.real,a.imaginary,in,out);

        for (int i = 0; i < in.numRows; i++) {
            for (int j = 0; j < in.numCols; j++) {
                in.get(i,j,b);
                out.get(i,j,found);

                ComplexMath64F.divide(a,b,expected);

                assertEquals(expected.real,found.real,1e-8);
                assertEquals(expected.imaginary,found.imaginary,1e-8);
            }
        }
    }

    @Test
    public void elementMinReal() {
        CDenseMatrix64F m = new CDenseMatrix64F(3,4);
        for (int i = 0; i < m.data.length; i++) {
            m.data[i] = -6 + i;
        }

        assertEquals(-6, CCommonOps.elementMinReal(m),1e-8);
    }

    @Test
    public void elementMinImaginary() {
        CDenseMatrix64F m = new CDenseMatrix64F(3,4);
        for (int i = 0; i < m.data.length; i++) {
            m.data[i] = -6 + i;
        }

        assertEquals(-5, CCommonOps.elementMinImaginary(m), 1e-8);
    }

    @Test
    public void elementMaxReal() {
        CDenseMatrix64F m = new CDenseMatrix64F(3,4);
        for (int i = 0; i < m.data.length; i++) {
            m.data[i] = -6 + i;
        }

        assertEquals(-6 + 11 * 2, CCommonOps.elementMaxReal(m), 1e-8);
    }

    @Test
    public void elementMaxImaginary() {
        CDenseMatrix64F m = new CDenseMatrix64F(3,4);
        for (int i = 0; i < m.data.length; i++) {
            m.data[i] = -6 + i;
        }

        assertEquals(-5 + 11 * 2, CCommonOps.elementMaxImaginary(m), 1e-8);
    }

}