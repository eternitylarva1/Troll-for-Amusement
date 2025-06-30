#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord;
uniform sampler2D u_texture;

void main() {
    // 获取怪物纹理颜色
    vec4 originalColor = texture2D(u_texture, v_texCoord);

    // 如果该像素透明，则直接丢弃
    if (originalColor.a == 0.0) {
        discard;
    }

    // 设置透明度为原值的50%
    vec4 transparentColor = vec4(originalColor.rgb, originalColor.a * 0.5);

    // 斜线效果参数
    float lineThickness = 0.03;
    float angle = radians(45.0);
    vec2 pos = v_texCoord - vec2(0.5);
    float d = cos(angle) * pos.x + sin(angle) * pos.y;
    float lineAlpha = smoothstep(-lineThickness * 0.5, lineThickness * 0.5, d);

    // 仅在有颜色的区域应用斜线
    lineAlpha *= originalColor.a;

    // 混合颜色
    vec4 finalColor = mix(transparentColor, vec4(1.0, 1.0, 1.0, lineAlpha), lineAlpha);

    gl_FragColor = finalColor;
}
