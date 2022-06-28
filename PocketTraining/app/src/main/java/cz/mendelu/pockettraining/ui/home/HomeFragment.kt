package cz.mendelu.pockettraining.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.pockettraining.architecture.BaseFragment
import cz.mendelu.pockettraining.databinding.FragmentHomeBinding
import cz.mendelu.pockettraining.databinding.RowPlanListBinding
import cz.mendelu.pockettraining.model.Plan


class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(HomeViewModel::class){

    private val planList: MutableList<Plan> = mutableListOf()
    private lateinit var adapter: PlansAdapter
    private lateinit var layoutManager: LinearLayoutManager

    inner class PlanDiffUtils(private val oldList: MutableList<Plan>,
                              private val newList: MutableList<Plan>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].planName == newList[newItemPosition].planName
        }
    }
    //ADAPTER
    inner class PlansAdapter : RecyclerView.Adapter<PlansAdapter.PlanViewHolder>(){

        inner class PlanViewHolder(val binding: RowPlanListBinding):
            RecyclerView.ViewHolder(binding.root){}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
            return PlanViewHolder(
                RowPlanListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
            val plan = planList.get(position)
            holder.binding.planName.text = plan.planName

            holder.binding.root.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToShowHomeList()
                action.planId = planList.get(holder.adapterPosition).id!!
                findNavController().navigate(action)
            }
        }

        override fun getItemCount(): Int = planList.size
    }

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun initViews() {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = PlansAdapter()
        binding.planList.layoutManager = layoutManager
        binding.planList.adapter = adapter

        viewModel
            .getPlanAll()
            .observe(viewLifecycleOwner, object : Observer<MutableList<Plan>>
            {
                override fun onChanged(t: MutableList<Plan>?){
                    val diffCallback = PlanDiffUtils(planList, t!!)
                    val diffResult = DiffUtil.calculateDiff(diffCallback)
                    diffResult.dispatchUpdatesTo(adapter)
                    planList.clear()
                    planList.addAll(t!!)
                }

            })
    }
    override fun onActivityCreated() {}
}
