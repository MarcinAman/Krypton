package org.kryptonmc.krypton.packet.out.play

import io.netty.buffer.ByteBuf
import org.kryptonmc.krypton.api.inventory.PlayerInventory
import org.kryptonmc.krypton.api.inventory.item.ItemStack
import org.kryptonmc.krypton.extension.writeVarInt
import org.kryptonmc.krypton.packet.state.PlayPacket
import org.kryptonmc.krypton.registry.Registries

class PacketOutWindowItems(private val inventory: PlayerInventory) : PlayPacket(0x13) {

    override fun write(buf: ByteBuf) {
        buf.writeVarInt(0)

        buf.writeShort(46)
        inventory.items.forEach { buf.writeItem(it) }
    }

    private fun ByteBuf.writeItem(item: ItemStack?) {
        if (item == null) {
            writeBoolean(false)
            return
        }
        writeBoolean(true)
        writeVarInt(Registries.BLOCKS.idOf(item.type.key))
        writeByte(item.amount)
        writeByte(0) // TAG_End
    }
}