/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public class ToneGenerator extends AudioMedia {
  private long swigCPtr;

  protected ToneGenerator(long cPtr, boolean cMemoryOwn) {
    super(pjsua2JNI.ToneGenerator_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ToneGenerator obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_ToneGenerator(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public ToneGenerator() {
    this(pjsua2JNI.new_ToneGenerator(), true);
  }

  public void createToneGenerator(long clock_rate, long channel_count) throws java.lang.Exception {
    pjsua2JNI.ToneGenerator_createToneGenerator__SWIG_0(swigCPtr, this, clock_rate, channel_count);
  }

  public void createToneGenerator(long clock_rate) throws java.lang.Exception {
    pjsua2JNI.ToneGenerator_createToneGenerator__SWIG_1(swigCPtr, this, clock_rate);
  }

  public void createToneGenerator() throws java.lang.Exception {
    pjsua2JNI.ToneGenerator_createToneGenerator__SWIG_2(swigCPtr, this);
  }

  public boolean isBusy() {
    return pjsua2JNI.ToneGenerator_isBusy(swigCPtr, this);
  }

  public void stop() throws java.lang.Exception {
    pjsua2JNI.ToneGenerator_stop(swigCPtr, this);
  }

  public void rewind() throws java.lang.Exception {
    pjsua2JNI.ToneGenerator_rewind(swigCPtr, this);
  }

  public void play(ToneDescVector tones, boolean loop) throws java.lang.Exception {
    pjsua2JNI.ToneGenerator_play__SWIG_0(swigCPtr, this, ToneDescVector.getCPtr(tones), tones, loop);
  }

  public void play(ToneDescVector tones) throws java.lang.Exception {
    pjsua2JNI.ToneGenerator_play__SWIG_1(swigCPtr, this, ToneDescVector.getCPtr(tones), tones);
  }

  public void playDigits(ToneDigitVector digits, boolean loop) throws java.lang.Exception {
    pjsua2JNI.ToneGenerator_playDigits__SWIG_0(swigCPtr, this, ToneDigitVector.getCPtr(digits), digits, loop);
  }

  public void playDigits(ToneDigitVector digits) throws java.lang.Exception {
    pjsua2JNI.ToneGenerator_playDigits__SWIG_1(swigCPtr, this, ToneDigitVector.getCPtr(digits), digits);
  }

  public ToneDigitMapVector getDigitMap() throws java.lang.Exception {
    return new ToneDigitMapVector(pjsua2JNI.ToneGenerator_getDigitMap(swigCPtr, this), true);
  }

  public void setDigitMap(ToneDigitMapVector digit_map) throws java.lang.Exception {
    pjsua2JNI.ToneGenerator_setDigitMap(swigCPtr, this, ToneDigitMapVector.getCPtr(digit_map), digit_map);
  }

}
