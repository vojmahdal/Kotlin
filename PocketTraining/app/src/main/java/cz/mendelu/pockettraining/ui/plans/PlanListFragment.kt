package cz.mendelu.pockettraining.ui.plans

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.pockettraining.architecture.BaseFragment
import cz.mendelu.pockettraining.architecture.BasePlanFragment
import cz.mendelu.pockettraining.databinding.FragmentPlanListBinding
import cz.mendelu.pockettraining.databinding.RowPlanListBinding
import cz.mendelu.pockettraining.model.Plan
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlanListFragment :
    BaseFragment<FragmentPlanListBinding, PlanListViewModel>(PlanListViewModel::class){

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
                val action = PlanListFragmentDirections.actionPlanListFragmentToPlanFragment()
                action.planId = planList.get(holder.adapterPosition).id!!
                findNavController().navigate(action)
            }
        }

        override fun getItemCount(): Int = planList.size
    }

    override val bindingInflater: (LayoutInflater) -> FragmentPlanListBinding
        get() = FragmentPlanListBinding::inflate

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
        binding.fabPlan.setOnClickListener{
           findNavController().navigate(PlanListFragmentDirections.actionPlanListFragmentToPlanFragment())
        }
    }

    override fun onActivityCreated() {}
    }