include /home/eldarh/Videos/pjproject-2.4.5/build.mak

LOCAL_PATH	:= $(PJDIR)/home/eldarh/Documents/Oyan6/Oyan
include $(CLEAR_VARS)

LOCAL_MODULE    := libpjsua2
LOCAL_CFLAGS    := $(APP_CXXFLAGS) -frtti -fexceptions
LOCAL_LDFLAGS   := $(APP_LDXXFLAGS)
LOCAL_LDLIBS    := $(APP_LDXXLIBS)
#LOCAL_SHARED_LIBRARIES := $(APP_LDXXLIBS)
LOCAL_SRC_FILES := /home/e/Documents/pjproject-2.4.5/pjsip-apps/src/swig/java/output/pjsua2_wrap.cpp

include $(BUILD_SHARED_LIBRARY)
