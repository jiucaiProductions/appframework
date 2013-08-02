package org.jiucai.appframework.common.util;

public class SwfInfo {

	public static final int UNCOMP_HDR_LEN = 8; // portion of header that is
												// never compressed

	private boolean isCompressed;
	private int version;
	private long size;
	private int width;
	private int height;
	private float fps;
	private int frameCount;

	public boolean isCompressed() {
		return isCompressed;
	}

	public void setCompressed(boolean isCompressed) {
		this.isCompressed = isCompressed;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getFps() {
		return fps;
	}

	public void setFps(float fps) {
		this.fps = fps;
	}

	public int getFrameCount() {
		return frameCount;
	}

	public void setFrameCount(int frameCount) {
		this.frameCount = frameCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SwfInfo [isCompressed=");
		builder.append(isCompressed);
		builder.append(", version=");
		builder.append(version);
		builder.append(", size=");
		builder.append(size);
		builder.append(", width=");
		builder.append(width);
		builder.append(", height=");
		builder.append(height);
		builder.append(", fps=");
		builder.append(fps);
		builder.append(", frameCount=");
		builder.append(frameCount);
		builder.append("]");
		return builder.toString();
	}

}
