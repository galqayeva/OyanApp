/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public class ToneDesc extends pjmedia_tone_desc {
  private long swigCPtr;

  protected ToneDesc(long cPtr, boolean cMemoryOwn) {
    super(pjsua2JNI.ToneDesc_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ToneDesc obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_ToneDesc(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public ToneDesc() {
    this(pjsua2JNI.new_ToneDesc(), true);
  }

}
