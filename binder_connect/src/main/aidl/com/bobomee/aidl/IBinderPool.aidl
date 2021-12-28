// IBinderPool.aidl
package com.bobomee.aidl;

// Declare any non-default types here with import statements

interface IBinderPool {

    IBinder queryBinder(String tag);
}
