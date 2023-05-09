package com.example.vu.data.java.filter;

public class SmoothingFilter {
    SmoothingFilter() {
    }

    // private final int m_sample_rate = 1000; // sampling rate in Hz
    // private final int m_cutoff_frequency = 5; // cutoff frequency in Hz

    // A and B for a frequency of 1000Hz and cutoff selected to be 10Hz
    final double[] m_a = { 1, -1.9112, 0.9150 };// denominator coefficients
    final double[] m_b = { 0.0009, 0.0019, 0.0009 };// numerator coefficients

    private double[] m_input_history = new double[2]; // input history buffer
    private double[] m_output_history = new double[2]; // output history buffer

    // Input:Noisy signal
    // Output:Filtered signal
    public double process(double input) {
        double output = 0.0;
        // compute output using filter coefficients and history buffers
        output = m_b[0] * input +
                m_b[1] * m_input_history[0] +
                m_b[2] * m_input_history[1] -
                m_a[1] * m_output_history[0] -
                m_a[2] * m_output_history[1];

        // update input and output history buffers
        m_input_history[1] = m_input_history[0]; // update delayed input
        m_input_history[0] = input; // update most recent input
        m_output_history[1] = m_output_history[0]; // update delayed output
        m_output_history[0] = output; // update most recent output

        return output;
    }
}
