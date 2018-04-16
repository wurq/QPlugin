
package com.arch.ipccenter.base;
// Declare any non-default types here with import statements

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IIpcCallback extends IInterface {
    /** Local-side IPC implementation stub class. */
    public static abstract class Stub extends Binder implements IIpcCallback {
        private static final String DESCRIPTOR = "com.arch.ipccenter.base.IIpcCallback";
        /** Construct the stub at attach it to the interface. */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.arch.ipccenter.IIpcCallback interface,
         * generating a proxy if needed.
         */
        public static IIpcCallback asInterface(IBinder obj) {
            if ((obj==null)) {
                return null;
            }

            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null)&&(iin instanceof IIpcCallback))) {
                return ((IIpcCallback)iin);
            }
            return new IIpcCallback.Stub.Proxy(obj);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case TRANSACTION_onIpcCallback: {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    Bundle _arg1;
                    if ((0!=data.readInt())) {
                        _arg1 = Bundle.CREATOR.createFromParcel(data);
                    }
                    else {
                        _arg1 = null;
                    }
                    Bundle _arg2;
                    if ((0!=data.readInt())) {
                        _arg2 = Bundle.CREATOR.createFromParcel(data);
                    }
                    else {
                        _arg2 = null;
                    }
                    int _result = this.onIpcCallback(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    if ((_arg2!=null)) {
                        reply.writeInt(1);
                        _arg2.writeToParcel(reply, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    }
                    else {
                        reply.writeInt(0);
                    }
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }


        private static class Proxy implements IIpcCallback {
            private IBinder mRemote;
            Proxy(IBinder remote) {
                mRemote = remote;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor()
            {
                return DESCRIPTOR;
            }

            @Override
            public int onIpcCallback(int callbackId, Bundle inBundle, Bundle inoutBundle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                int _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(callbackId);
                    if ((inBundle!=null)) {
                        _data.writeInt(1);
                        inBundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if ((inoutBundle!=null)) {
                        _data.writeInt(1);
                        inoutBundle.writeToParcel(_data, 0);
                    }
                    else {
                        _data.writeInt(0);
                    }
                    mRemote.transact(Stub.TRANSACTION_onIpcCallback, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readInt();
                    if ((0!=_reply.readInt())) {
                        inoutBundle.readFromParcel(_reply);
                    }
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
        }

        static final int TRANSACTION_onIpcCallback = (IBinder.FIRST_CALL_TRANSACTION + 0);
    }

    public int onIpcCallback(int callbackId, Bundle inBundle,
                             Bundle inoutBundle) throws RemoteException;
}
