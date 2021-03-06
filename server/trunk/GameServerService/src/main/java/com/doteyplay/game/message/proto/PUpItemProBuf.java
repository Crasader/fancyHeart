// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: upItem.proto

package com.doteyplay.game.message.proto;

public final class PUpItemProBuf {
  private PUpItemProBuf() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface PUpItemOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // repeated .PItemChangeLog itemLogList = 1;
    /**
     * <code>repeated .PItemChangeLog itemLogList = 1;</code>
     */
    java.util.List<com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog> 
        getItemLogListList();
    /**
     * <code>repeated .PItemChangeLog itemLogList = 1;</code>
     */
    com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog getItemLogList(int index);
    /**
     * <code>repeated .PItemChangeLog itemLogList = 1;</code>
     */
    int getItemLogListCount();
    /**
     * <code>repeated .PItemChangeLog itemLogList = 1;</code>
     */
    java.util.List<? extends com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLogOrBuilder> 
        getItemLogListOrBuilderList();
    /**
     * <code>repeated .PItemChangeLog itemLogList = 1;</code>
     */
    com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLogOrBuilder getItemLogListOrBuilder(
        int index);
  }
  /**
   * Protobuf type {@code PUpItem}
   */
  public static final class PUpItem extends
      com.google.protobuf.GeneratedMessage
      implements PUpItemOrBuilder {
    // Use PUpItem.newBuilder() to construct.
    private PUpItem(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private PUpItem(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final PUpItem defaultInstance;
    public static PUpItem getDefaultInstance() {
      return defaultInstance;
    }

    public PUpItem getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private PUpItem(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
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
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                itemLogList_ = new java.util.ArrayList<com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog>();
                mutable_bitField0_ |= 0x00000001;
              }
              itemLogList_.add(input.readMessage(com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog.PARSER, extensionRegistry));
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
          itemLogList_ = java.util.Collections.unmodifiableList(itemLogList_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.doteyplay.game.message.proto.PUpItemProBuf.internal_static_PUpItem_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.doteyplay.game.message.proto.PUpItemProBuf.internal_static_PUpItem_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem.class, com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem.Builder.class);
    }

    public static com.google.protobuf.Parser<PUpItem> PARSER =
        new com.google.protobuf.AbstractParser<PUpItem>() {
      public PUpItem parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new PUpItem(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<PUpItem> getParserForType() {
      return PARSER;
    }

    // repeated .PItemChangeLog itemLogList = 1;
    public static final int ITEMLOGLIST_FIELD_NUMBER = 1;
    private java.util.List<com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog> itemLogList_;
    /**
     * <code>repeated .PItemChangeLog itemLogList = 1;</code>
     */
    public java.util.List<com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog> getItemLogListList() {
      return itemLogList_;
    }
    /**
     * <code>repeated .PItemChangeLog itemLogList = 1;</code>
     */
    public java.util.List<? extends com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLogOrBuilder> 
        getItemLogListOrBuilderList() {
      return itemLogList_;
    }
    /**
     * <code>repeated .PItemChangeLog itemLogList = 1;</code>
     */
    public int getItemLogListCount() {
      return itemLogList_.size();
    }
    /**
     * <code>repeated .PItemChangeLog itemLogList = 1;</code>
     */
    public com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog getItemLogList(int index) {
      return itemLogList_.get(index);
    }
    /**
     * <code>repeated .PItemChangeLog itemLogList = 1;</code>
     */
    public com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLogOrBuilder getItemLogListOrBuilder(
        int index) {
      return itemLogList_.get(index);
    }

    private void initFields() {
      itemLogList_ = java.util.Collections.emptyList();
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      for (int i = 0; i < getItemLogListCount(); i++) {
        if (!getItemLogList(i).isInitialized()) {
          memoizedIsInitialized = 0;
          return false;
        }
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      for (int i = 0; i < itemLogList_.size(); i++) {
        output.writeMessage(1, itemLogList_.get(i));
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      for (int i = 0; i < itemLogList_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, itemLogList_.get(i));
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code PUpItem}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.doteyplay.game.message.proto.PUpItemProBuf.PUpItemOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.doteyplay.game.message.proto.PUpItemProBuf.internal_static_PUpItem_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.doteyplay.game.message.proto.PUpItemProBuf.internal_static_PUpItem_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem.class, com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem.Builder.class);
      }

      // Construct using com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
          getItemLogListFieldBuilder();
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        if (itemLogListBuilder_ == null) {
          itemLogList_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          itemLogListBuilder_.clear();
        }
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.doteyplay.game.message.proto.PUpItemProBuf.internal_static_PUpItem_descriptor;
      }

      public com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem getDefaultInstanceForType() {
        return com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem.getDefaultInstance();
      }

      public com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem build() {
        com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem buildPartial() {
        com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem result = new com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem(this);
        int from_bitField0_ = bitField0_;
        if (itemLogListBuilder_ == null) {
          if (((bitField0_ & 0x00000001) == 0x00000001)) {
            itemLogList_ = java.util.Collections.unmodifiableList(itemLogList_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.itemLogList_ = itemLogList_;
        } else {
          result.itemLogList_ = itemLogListBuilder_.build();
        }
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem) {
          return mergeFrom((com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem other) {
        if (other == com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem.getDefaultInstance()) return this;
        if (itemLogListBuilder_ == null) {
          if (!other.itemLogList_.isEmpty()) {
            if (itemLogList_.isEmpty()) {
              itemLogList_ = other.itemLogList_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureItemLogListIsMutable();
              itemLogList_.addAll(other.itemLogList_);
            }
            onChanged();
          }
        } else {
          if (!other.itemLogList_.isEmpty()) {
            if (itemLogListBuilder_.isEmpty()) {
              itemLogListBuilder_.dispose();
              itemLogListBuilder_ = null;
              itemLogList_ = other.itemLogList_;
              bitField0_ = (bitField0_ & ~0x00000001);
              itemLogListBuilder_ = 
                com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                   getItemLogListFieldBuilder() : null;
            } else {
              itemLogListBuilder_.addAllMessages(other.itemLogList_);
            }
          }
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        for (int i = 0; i < getItemLogListCount(); i++) {
          if (!getItemLogList(i).isInitialized()) {
            
            return false;
          }
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.doteyplay.game.message.proto.PUpItemProBuf.PUpItem) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // repeated .PItemChangeLog itemLogList = 1;
      private java.util.List<com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog> itemLogList_ =
        java.util.Collections.emptyList();
      private void ensureItemLogListIsMutable() {
        if (!((bitField0_ & 0x00000001) == 0x00000001)) {
          itemLogList_ = new java.util.ArrayList<com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog>(itemLogList_);
          bitField0_ |= 0x00000001;
         }
      }

      private com.google.protobuf.RepeatedFieldBuilder<
          com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog, com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog.Builder, com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLogOrBuilder> itemLogListBuilder_;

      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public java.util.List<com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog> getItemLogListList() {
        if (itemLogListBuilder_ == null) {
          return java.util.Collections.unmodifiableList(itemLogList_);
        } else {
          return itemLogListBuilder_.getMessageList();
        }
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public int getItemLogListCount() {
        if (itemLogListBuilder_ == null) {
          return itemLogList_.size();
        } else {
          return itemLogListBuilder_.getCount();
        }
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog getItemLogList(int index) {
        if (itemLogListBuilder_ == null) {
          return itemLogList_.get(index);
        } else {
          return itemLogListBuilder_.getMessage(index);
        }
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public Builder setItemLogList(
          int index, com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog value) {
        if (itemLogListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureItemLogListIsMutable();
          itemLogList_.set(index, value);
          onChanged();
        } else {
          itemLogListBuilder_.setMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public Builder setItemLogList(
          int index, com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog.Builder builderForValue) {
        if (itemLogListBuilder_ == null) {
          ensureItemLogListIsMutable();
          itemLogList_.set(index, builderForValue.build());
          onChanged();
        } else {
          itemLogListBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public Builder addItemLogList(com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog value) {
        if (itemLogListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureItemLogListIsMutable();
          itemLogList_.add(value);
          onChanged();
        } else {
          itemLogListBuilder_.addMessage(value);
        }
        return this;
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public Builder addItemLogList(
          int index, com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog value) {
        if (itemLogListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureItemLogListIsMutable();
          itemLogList_.add(index, value);
          onChanged();
        } else {
          itemLogListBuilder_.addMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public Builder addItemLogList(
          com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog.Builder builderForValue) {
        if (itemLogListBuilder_ == null) {
          ensureItemLogListIsMutable();
          itemLogList_.add(builderForValue.build());
          onChanged();
        } else {
          itemLogListBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public Builder addItemLogList(
          int index, com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog.Builder builderForValue) {
        if (itemLogListBuilder_ == null) {
          ensureItemLogListIsMutable();
          itemLogList_.add(index, builderForValue.build());
          onChanged();
        } else {
          itemLogListBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public Builder addAllItemLogList(
          java.lang.Iterable<? extends com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog> values) {
        if (itemLogListBuilder_ == null) {
          ensureItemLogListIsMutable();
          super.addAll(values, itemLogList_);
          onChanged();
        } else {
          itemLogListBuilder_.addAllMessages(values);
        }
        return this;
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public Builder clearItemLogList() {
        if (itemLogListBuilder_ == null) {
          itemLogList_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          itemLogListBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public Builder removeItemLogList(int index) {
        if (itemLogListBuilder_ == null) {
          ensureItemLogListIsMutable();
          itemLogList_.remove(index);
          onChanged();
        } else {
          itemLogListBuilder_.remove(index);
        }
        return this;
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog.Builder getItemLogListBuilder(
          int index) {
        return getItemLogListFieldBuilder().getBuilder(index);
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLogOrBuilder getItemLogListOrBuilder(
          int index) {
        if (itemLogListBuilder_ == null) {
          return itemLogList_.get(index);  } else {
          return itemLogListBuilder_.getMessageOrBuilder(index);
        }
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public java.util.List<? extends com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLogOrBuilder> 
           getItemLogListOrBuilderList() {
        if (itemLogListBuilder_ != null) {
          return itemLogListBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(itemLogList_);
        }
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog.Builder addItemLogListBuilder() {
        return getItemLogListFieldBuilder().addBuilder(
            com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog.getDefaultInstance());
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog.Builder addItemLogListBuilder(
          int index) {
        return getItemLogListFieldBuilder().addBuilder(
            index, com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog.getDefaultInstance());
      }
      /**
       * <code>repeated .PItemChangeLog itemLogList = 1;</code>
       */
      public java.util.List<com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog.Builder> 
           getItemLogListBuilderList() {
        return getItemLogListFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilder<
          com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog, com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog.Builder, com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLogOrBuilder> 
          getItemLogListFieldBuilder() {
        if (itemLogListBuilder_ == null) {
          itemLogListBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
              com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog, com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog.Builder, com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLogOrBuilder>(
                  itemLogList_,
                  ((bitField0_ & 0x00000001) == 0x00000001),
                  getParentForChildren(),
                  isClean());
          itemLogList_ = null;
        }
        return itemLogListBuilder_;
      }

      // @@protoc_insertion_point(builder_scope:PUpItem)
    }

    static {
      defaultInstance = new PUpItem(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:PUpItem)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_PUpItem_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_PUpItem_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014upItem.proto\032\nitem.proto\"/\n\007PUpItem\022$\n" +
      "\013itemLogList\030\001 \003(\0132\017.PItemChangeLogB1\n c" +
      "om.doteyplay.game.message.protoB\rPUpItem" +
      "ProBuf"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_PUpItem_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_PUpItem_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_PUpItem_descriptor,
              new java.lang.String[] { "ItemLogList", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.doteyplay.game.message.proto.ItemProBuf.getDescriptor(),
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
