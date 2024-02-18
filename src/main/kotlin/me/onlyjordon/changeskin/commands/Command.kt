package me.onlyjordon.changeskin.commands

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.HumanEntity
import org.bukkit.permissions.Permission
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors

abstract class Command @JvmOverloads constructor(name: String?, permission: String? = "", aliases: List<String?>? = ArrayList()) : Command(name!!), CommandExecutor, TabCompleter {
    @Transient
    val usageStrings: Map<String, String>


    constructor(name: String?, permission: Permission) : this(name, permission.name)

    constructor(name: String?, aliases: List<String?>?) : this(name, null, aliases)

    init {
        if (permission != "") {
            this.permission = permission
        }
        if (aliases != null && aliases.isEmpty()) {
            this.setAliases(aliases)
        }
        try {
            val activeAliases = Command::class.java.getDeclaredField("activeAliases")
            activeAliases.isAccessible = true
            activeAliases[this] = aliases
            val al = Command::class.java.getDeclaredField("aliases")
            al.isAccessible = true
            al[this] = aliases
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        this.usageStrings = LinkedHashMap()
    }

    constructor(name: String?, permission: String?, aliases: Array<String?>?) : this(name, permission, Arrays.stream<String?>(aliases).collect(Collectors.toList<String?>()))


    //    @NotNull
    //    @Override
    //    public List<String> tabComplete(CommandSender sender, String alias, String[] args, @Nullable Location location) throws IllegalArgumentException {
    //        return tabComplete(sender, alias, args);
    //    }
    @Throws(IllegalArgumentException::class)
    override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>, location: Location?): List<String> {
        return tabComplete(sender, args)
    }

    fun tabComplete(sender: CommandSender, args: Array<String>): List<String> {
        return tabComplete(sender, name, args)
    }

    @Throws(IllegalArgumentException::class)
    override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>): List<String> {
        var tabs: MutableList<String>?
        try {
            tabs = onTabComplete(sender, this, alias, args)
        } catch (e: Exception) {
            throw RuntimeException("There was an error tab-completing command " + alias + " for " + sender.name + ". Full command: (/" + alias + " " + java.lang.String.join(" ", *args) + ")", e)
        }
        if (tabs == null) {
            tabs = Bukkit.getOnlinePlayers().stream().map { obj: HumanEntity -> obj.name }.collect(Collectors.toList())
        }
        return tabs ?: ArrayList();
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        return onCommand(sender, this, commandLabel, args)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (!testPermission(sender)) return false
        return execute(sender, command, label, args)
    }

    protected abstract fun execute(sender: CommandSender, command: Command?, label: String?, args: Array<String>): Boolean

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): MutableList<String>? {
        if (!this.testPermission(sender)) return null
        val allTabs = completeTab(sender, alias, args) ?: return null
        if (allTabs.isEmpty()) return ArrayList()
        val currentTabs: MutableList<String> = ArrayList()
        allTabs.keys.forEach(Consumer<String> forEach@{ tab: String ->
            val arg = allTabs[tab]!!
            if (args.size == 0 && arg == 0) {
                currentTabs.add(tab)
            }
            if (args.size - 1 != arg) return@forEach
            if (args.size == 1) {
                if (tab.length < args[arg].length) return@forEach

                val currentArg = args[arg]
                val length = currentArg.length
                if (length == 0) {
                    currentTabs.add(tab)
                    return@forEach
                }
                val argument1 = tab.substring(0, length)
                if (currentArg.equals(argument1, ignoreCase = true)) {
                    currentTabs.add(tab)
                }
            } else if (args.size >= 1 && arg >= 1) {
                if (args[arg].isEmpty()) {
                    currentTabs.add(tab)
                } else {
                    if (tab.length < args[arg].length) return@forEach

                    val currentArg = args[arg]
                    val length = currentArg.length
                    if (length == 0) {
                        currentTabs.add(tab)
                        return@forEach
                    }
                    val argument1 = tab.substring(0, length)
                    if (currentArg.equals(argument1, ignoreCase = true)) {
                        currentTabs.add(tab)
                    }
                }
            }
        })
        return currentTabs
    }


    abstract fun completeTab(sender: CommandSender, alias: String?, args: Array<String>?): HashMap<String, Int>?
}
