#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoord;

uniform sampler2D u_texture;
uniform vec2 u_resolution;
uniform sampler2D u_buffer;

// inspired by Camouflag on shadertoy

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void main(){
    vec2 uv = v_texCoord;
    vec2 screenCoord = uv * u_resolution; // 将归一化坐标转换为像素坐标

    // 使用邻域采样来平滑 bgMask
    vec2 bgMask = texture2D(u_buffer, uv).xy;
    bgMask += texture2D(u_buffer, uv + vec2(1.0 / u_resolution.x, 1.0)).xy;
    bgMask += texture2D(u_buffer, uv - vec2(1.0 / u_resolution.x, 1.0)).xy;
    bgMask += texture2D(u_buffer, uv + vec2(0.0, 1.0 / u_resolution.y)).xy;
    bgMask += texture2D(u_buffer, uv - vec2(0.0, 1.0 / u_resolution.y)).xy;
    bgMask /= 5.0; // 平均值

    bgMask -= texture2D(u_buffer, u_resolution).xy;

    // 添加噪声来减少闪烁
    float noise = rand(uv) * 0.005;
    bgMask += vec2(noise, noise);

    // sample the texture based on coord subtracted by scaled buffer's uv
    vec4 color = texture2D(u_texture, uv - bgMask * 0.01);

    gl_FragColor = color;
}
