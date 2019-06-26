package com.codingtive.dicodingmade.mvp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BarPresenterTest {
    @Mock
    private BarPresenter presenter;
    private BarView view;

    @Before
    public void setUp() {
        view = mock(BarView.class);
        presenter = new BarPresenter(view);
    }

    @Test
    public void calculateVolume() {
        double volume = presenter.volume(2, 2, 2);
        assertEquals(8.0, volume, 0.0001);
    }

    @Test
    public void testVolumeWithDoubleInput() {
        double volume = presenter.volume(2.3, 8.1, 2.9);
        assertEquals(54.026999999999994, volume, 0.0001);
    }

    @Test
    public void testVolumeWithZeroInput() {
        double volume = presenter.volume(0, 0, 0);
        assertEquals(0.0, volume, 0.0001);
    }

    @Test
    public void testCalculateVolume() {
        presenter.calculateVolume(2, 2, 2);
        verify(view).showVolume(any(BarModel.class));
    }
}
