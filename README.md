# AndroidFFmpegKit

提供Android上的FFmpeg Shell运行方式，使用7zip压缩ffmpeg，讲13m的全量ffmpeg降为3m。

1. ffmpeg编译自[ffmpeg-android](https://github.com/WritingMinds/ffmpeg-android)
2. 7zip使用[AndroidUn7zip](https://github.com/hzy3774/AndroidUn7zip) 略魔改 修复低版本error问题

### Usage

```
FFmpegKit.inst().init(context);
FFmpegKit.inst().execute(cmd, listener);
FFmpegKit.inst().exec(cmd) => Process
```

