// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: BaseResponseProto.proto

package com.edi.im.common.protocol;

import com.google.protobuf.GeneratedMessageV3;

public final class IMResponseProto {
    private IMResponseProto() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions(
                (com.google.protobuf.ExtensionRegistryLite) registry);
    }

    public interface IMResProtocolOrBuilder extends
            // @@protoc_insertion_point(interface_extends:protocol.IMResProtocol)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <code>required int64 responseId = 2;</code>
         */
        boolean hasResponseId();

        /**
         * <code>required int64 responseId = 2;</code>
         */
        long getResponseId();

        /**
         * <code>required string resMsg = 1;</code>
         */
        boolean hasResMsg();

        /**
         * <code>required string resMsg = 1;</code>
         */
        String getResMsg();

        /**
         * <code>required string resMsg = 1;</code>
         */
        com.google.protobuf.ByteString
        getResMsgBytes();

        /**
         * <code>required int32 type = 3;</code>
         */
        boolean hasType();

        /**
         * <code>required int32 type = 3;</code>
         */
        int getType();
    }

    /**
     * Protobuf type {@code protocol.IMResProtocol}
     */
    public static final class IMResProtocol extends GeneratedMessageV3 implements IMResProtocolOrBuilder {
        private static final long serialVersionUID = 0L;

        private IMResProtocol(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private IMResProtocol() {
            responseId_ = 0L;
            resMsg_ = "";
            type_ = 0;
        }

        @Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }

        private IMResProtocol(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownField(
                                    input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                        case 10: {
                            com.google.protobuf.ByteString bs = input.readBytes();
                            bitField0_ |= 0x00000002;
                            resMsg_ = bs;
                            break;
                        }
                        case 16: {
                            bitField0_ |= 0x00000001;
                            responseId_ = input.readInt64();
                            break;
                        }
                        case 24: {
                            bitField0_ |= 0x00000004;
                            type_ = input.readInt32();
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return IMResponseProto.internal_static_protocol_IMResProtocol_descriptor;
        }

        protected FieldAccessorTable
        internalGetFieldAccessorTable() {
            return IMResponseProto.internal_static_protocol_IMResProtocol_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            IMResProtocol.class, Builder.class);
        }

        private int bitField0_;
        public static final int RESPONSEID_FIELD_NUMBER = 2;
        private long responseId_;

        /**
         * <code>required int64 responseId = 2;</code>
         */
        public boolean hasResponseId() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }

        /**
         * <code>required int64 responseId = 2;</code>
         */
        public long getResponseId() {
            return responseId_;
        }

        public static final int RESMSG_FIELD_NUMBER = 1;
        private volatile Object resMsg_;

        /**
         * <code>required string resMsg = 1;</code>
         */
        public boolean hasResMsg() {
            return ((bitField0_ & 0x00000002) == 0x00000002);
        }

        /**
         * <code>required string resMsg = 1;</code>
         */
        public String getResMsg() {
            Object ref = resMsg_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    resMsg_ = s;
                }
                return s;
            }
        }

        /**
         * <code>required string resMsg = 1;</code>
         */
        public com.google.protobuf.ByteString
        getResMsgBytes() {
            Object ref = resMsg_;
            if (ref instanceof String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (String) ref);
                resMsg_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        public static final int TYPE_FIELD_NUMBER = 3;
        private int type_;

        /**
         * <code>required int32 type = 3;</code>
         */
        public boolean hasType() {
            return ((bitField0_ & 0x00000004) == 0x00000004);
        }

        /**
         * <code>required int32 type = 3;</code>
         */
        public int getType() {
            return type_;
        }

        private byte memoizedIsInitialized = -1;

        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) return true;
            if (isInitialized == 0) return false;

            if (!hasResponseId()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasResMsg()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasType()) {
                memoizedIsInitialized = 0;
                return false;
            }
            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                com.google.protobuf.GeneratedMessageV3.writeString(output, 1, resMsg_);
            }
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                output.writeInt64(2, responseId_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                output.writeInt32(3, type_);
            }
            unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1) return size;

            size = 0;
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, resMsg_);
            }
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeInt64Size(2, responseId_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeInt32Size(3, type_);
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof IMResProtocol)) {
                return super.equals(obj);
            }
            IMResProtocol other = (IMResProtocol) obj;

            boolean result = true;
            result = result && (hasResponseId() == other.hasResponseId());
            if (hasResponseId()) {
                result = result && (getResponseId()
                        == other.getResponseId());
            }
            result = result && (hasResMsg() == other.hasResMsg());
            if (hasResMsg()) {
                result = result && getResMsg()
                        .equals(other.getResMsg());
            }
            result = result && (hasType() == other.hasType());
            if (hasType()) {
                result = result && (getType()
                        == other.getType());
            }
            result = result && unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptor().hashCode();
            if (hasResponseId()) {
                hash = (37 * hash) + RESPONSEID_FIELD_NUMBER;
                hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
                        getResponseId());
            }
            if (hasResMsg()) {
                hash = (37 * hash) + RESMSG_FIELD_NUMBER;
                hash = (53 * hash) + getResMsg().hashCode();
            }
            if (hasType()) {
                hash = (37 * hash) + TYPE_FIELD_NUMBER;
                hash = (53 * hash) + getType();
            }
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static IMResProtocol parseFrom(
                java.nio.ByteBuffer data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static IMResProtocol parseFrom(
                java.nio.ByteBuffer data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static IMResProtocol parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static IMResProtocol parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static IMResProtocol parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static IMResProtocol parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static IMResProtocol parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static IMResProtocol parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static IMResProtocol parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input);
        }

        public static IMResProtocol parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static IMResProtocol parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static IMResProtocol parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(IMResProtocol prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE
                    ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(
                BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        /**
         * Protobuf type {@code protocol.IMResProtocol}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:protocol.IMResProtocol)
                IMResProtocolOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return IMResponseProto.internal_static_protocol_IMResProtocol_descriptor;
            }

            protected FieldAccessorTable
            internalGetFieldAccessorTable() {
                return IMResponseProto.internal_static_protocol_IMResProtocol_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                IMResProtocol.class, Builder.class);
            }

            // Construct using com.edi.im.common.protocol.IMResponseProto.IMResProtocol.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessageV3
                        .alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                responseId_ = 0L;
                bitField0_ = (bitField0_ & ~0x00000001);
                resMsg_ = "";
                bitField0_ = (bitField0_ & ~0x00000002);
                type_ = 0;
                bitField0_ = (bitField0_ & ~0x00000004);
                return this;
            }

            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return IMResponseProto.internal_static_protocol_IMResProtocol_descriptor;
            }

            public IMResProtocol getDefaultInstanceForType() {
                return IMResProtocol.getDefaultInstance();
            }

            public IMResProtocol build() {
                IMResProtocol result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public IMResProtocol buildPartial() {
                IMResProtocol result = new IMResProtocol(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.responseId_ = responseId_;
                if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
                    to_bitField0_ |= 0x00000002;
                }
                result.resMsg_ = resMsg_;
                if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
                    to_bitField0_ |= 0x00000004;
                }
                result.type_ = type_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder clone() {
                return (Builder) super.clone();
            }

            public Builder setField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return (Builder) super.setField(field, value);
            }

            public Builder clearField(
                    com.google.protobuf.Descriptors.FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            public Builder clearOneof(
                    com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            public Builder setRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    int index, Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            public Builder addRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof IMResProtocol) {
                    return mergeFrom((IMResProtocol) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(IMResProtocol other) {
                if (other == IMResProtocol.getDefaultInstance()) return this;
                if (other.hasResponseId()) {
                    setResponseId(other.getResponseId());
                }
                if (other.hasResMsg()) {
                    bitField0_ |= 0x00000002;
                    resMsg_ = other.resMsg_;
                    onChanged();
                }
                if (other.hasType()) {
                    setType(other.getType());
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            public final boolean isInitialized() {
                if (!hasResponseId()) {
                    return false;
                }
                if (!hasResMsg()) {
                    return false;
                }
                if (!hasType()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                IMResProtocol parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (IMResProtocol) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int bitField0_;

            private long responseId_;

            /**
             * <code>required int64 responseId = 2;</code>
             */
            public boolean hasResponseId() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }

            /**
             * <code>required int64 responseId = 2;</code>
             */
            public long getResponseId() {
                return responseId_;
            }

            /**
             * <code>required int64 responseId = 2;</code>
             */
            public Builder setResponseId(long value) {
                bitField0_ |= 0x00000001;
                responseId_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int64 responseId = 2;</code>
             */
            public Builder clearResponseId() {
                bitField0_ = (bitField0_ & ~0x00000001);
                responseId_ = 0L;
                onChanged();
                return this;
            }

            private Object resMsg_ = "";

            /**
             * <code>required string resMsg = 1;</code>
             */
            public boolean hasResMsg() {
                return ((bitField0_ & 0x00000002) == 0x00000002);
            }

            /**
             * <code>required string resMsg = 1;</code>
             */
            public String getResMsg() {
                Object ref = resMsg_;
                if (!(ref instanceof String)) {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        resMsg_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>required string resMsg = 1;</code>
             */
            public com.google.protobuf.ByteString
            getResMsgBytes() {
                Object ref = resMsg_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (String) ref);
                    resMsg_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            /**
             * <code>required string resMsg = 1;</code>
             */
            public Builder setResMsg(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000002;
                resMsg_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required string resMsg = 1;</code>
             */
            public Builder clearResMsg() {
                bitField0_ = (bitField0_ & ~0x00000002);
                resMsg_ = getDefaultInstance().getResMsg();
                onChanged();
                return this;
            }

            /**
             * <code>required string resMsg = 1;</code>
             */
            public Builder setResMsgBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000002;
                resMsg_ = value;
                onChanged();
                return this;
            }

            private int type_;

            /**
             * <code>required int32 type = 3;</code>
             */
            public boolean hasType() {
                return ((bitField0_ & 0x00000004) == 0x00000004);
            }

            /**
             * <code>required int32 type = 3;</code>
             */
            public int getType() {
                return type_;
            }

            /**
             * <code>required int32 type = 3;</code>
             */
            public Builder setType(int value) {
                bitField0_ |= 0x00000004;
                type_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 type = 3;</code>
             */
            public Builder clearType() {
                bitField0_ = (bitField0_ & ~0x00000004);
                type_ = 0;
                onChanged();
                return this;
            }

            public final Builder setUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }


            // @@protoc_insertion_point(builder_scope:protocol.IMResProtocol)
        }

        // @@protoc_insertion_point(class_scope:protocol.IMResProtocol)
        private static final IMResProtocol DEFAULT_INSTANCE;

        static {
            DEFAULT_INSTANCE = new IMResProtocol();
        }

        public static IMResProtocol getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        @Deprecated
        public static final com.google.protobuf.Parser<IMResProtocol>
                PARSER = new com.google.protobuf.AbstractParser<IMResProtocol>() {
            public IMResProtocol parsePartialFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return new IMResProtocol(input, extensionRegistry);
            }
        };

        public static com.google.protobuf.Parser<IMResProtocol> parser() {
            return PARSER;
        }

        @Override
        public com.google.protobuf.Parser<IMResProtocol> getParserForType() {
            return PARSER;
        }

        public IMResProtocol getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

    }

    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_protocol_IMResProtocol_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_protocol_IMResProtocol_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }

    private static com.google.protobuf.Descriptors.FileDescriptor
            descriptor;

    static {
        String[] descriptorData = {
                "\n\027BaseResponseProto.proto\022\010protocol\"B\n\016C" +
                        "IMResProtocol\022\022\n\nresponseId\030\002 \002(\003\022\016\n\006res" +
                        "Msg\030\001 \002(\t\022\014\n\004type\030\003 \002(\005B8\n$com.crossover" +
                        "jie.cim.common.protocolB\020CIMResponseProt" +
                        "o"
        };
        com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
                new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
                    public com.google.protobuf.ExtensionRegistry assignDescriptors(
                            com.google.protobuf.Descriptors.FileDescriptor root) {
                        descriptor = root;
                        return null;
                    }
                };
        com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[]{
                        }, assigner);
        internal_static_protocol_IMResProtocol_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_protocol_IMResProtocol_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_protocol_IMResProtocol_descriptor,
                new String[]{"ResponseId", "ResMsg", "Type",});
    }

    // @@protoc_insertion_point(outer_class_scope)
}
