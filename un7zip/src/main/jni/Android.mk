JNI_PATH := $(call my-dir)

LOCAL_PATH := $(JNI_PATH)

include $(CLEAR_VARS)

LOCAL_C_INCLUDES := $(LOCAL_PATH)/src/

LOCAL_MODULE := un7zip

LOCAL_CFLAGS := -DDEBUG

APP_PLATFORM := android-14

APP_STL := gnustl_shared
				
LOCAL_SRC_FILES := \
		./src/7zAlloc.c \
		./src/7zBuf.c \
		./src/7zCrc.c \
		./src/7zCrcOpt.c \
		./src/7zDec.c \
		./src/7zFile.c \
		./src/7zIn.c \
		./src/7zStream.c \
		./src/Bcj2.c \
		./src/Bra.c \
		./src/Bra86.c \
		./src/CpuArch.c \
		./src/Lzma2Dec.c \
		./src/LzmaDec.c \
		./src/Ppmd7.c \
		./src/Ppmd7Dec.c \
		./src/7zMain.c \
		./andun7z.cpp
        
LOCAL_LDLIBS := -llog 

include $(BUILD_SHARED_LIBRARY)

