#version 400 core

in vec4 vColor;
in float vTextureID;
in vec2 vTextureCoord;

uniform sampler2D[_PAPERCLIP_MAX_TEXTURE_SLOTS_] uTextures;

out vec4 outColor;

void main() {

    int textureID = int(vTextureID);

    outColor = vColor * texture(uTextures[0], vTextureCoord);
}